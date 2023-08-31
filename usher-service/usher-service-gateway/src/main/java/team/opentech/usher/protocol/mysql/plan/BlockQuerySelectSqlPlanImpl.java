package team.opentech.usher.protocol.mysql.plan;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLCharExpr;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import team.opentech.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import team.opentech.usher.pojo.cqe.InvokeCommand;
import team.opentech.usher.pojo.cqe.InvokeCommandBuilder;
import team.opentech.usher.service.GatewaySdkService;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.SpringUtil;
import team.opentech.usher.util.StringUtil;

/**
 * 简单sql执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时31分
 */
public class BlockQuerySelectSqlPlanImpl extends BlockQuerySelectSqlPlan {

    /**
     * 节点
     */
    private final GatewaySdkService gatewaySdkService;


    public BlockQuerySelectSqlPlanImpl(SqlTableSourceBinaryTreeInfo froms, Map<String, String> headers, Map<String, Object> params) {
        super(froms, headers, params);
        this.gatewaySdkService = SpringUtil.getBean(GatewaySdkService.class);
    }

    @Override
    public NodeInvokeResult invoke() {
        InvokeCommandBuilder invokeCommandBuilder = new InvokeCommandBuilder();
        invokeCommandBuilder.setType(InvokeTypeEnum.MYSQL.getCode());
        invokeCommandBuilder.addArgs(params);
        invokeCommandBuilder.addHeader(headers);
        SQLExprTableSource tableSource = froms.getTableSource();
        invokeCommandBuilder.addAlias(tableSource.getAlias());
        SQLPropertyExpr expr = (SQLPropertyExpr) tableSource.getExpr();
        String owner = expr.getOwnernName();
        String name = expr.getName();
        if (name.startsWith("&")) {
            Long resultIndex = Long.parseLong(name.substring(1));
            return lastAllPlanResult.get(resultIndex);
        }
        List<SQLBinaryOpExpr> where = froms.getWhere();
        Map<String, Object> whereParams = new HashMap<>();
        Boolean haveResult = true;
        if (where != null) {
            for (SQLBinaryOpExpr sqlBinaryOpExpr : where) {
                SQLExpr left = sqlBinaryOpExpr.getLeft();
                SQLExpr right = sqlBinaryOpExpr.getRight();
                // where两边都不是属性的时候直接忽略
                if (!(left instanceof SQLIdentifierExpr) && !(right instanceof SQLIdentifierExpr)) {
                    // 如果两边不一致,则无结果
                    if (!Objects.equals(left.toString(), right.toString())) {
                        haveResult = false;
                    }
                    continue;
                }
                String rightStr = right.toString();
                if (right instanceof SQLCharExpr) {
                    rightStr = ((SQLCharExpr) right).getText();
                }
                whereParams.put(left.toString(), rightStr);
            }
        }
        invokeCommandBuilder.addArgs(whereParams);
        StringBuilder path = new StringBuilder();
        String database = MysqlContent.MYSQL_TCP_INFO.get().getDatabase();
        if (owner != null) {
            path.append(owner);
            path.append(MysqlContent.PATH_SEPARATOR);
        } else if (StringUtil.isNotEmpty(database)) {
            path.append(database);
            path.append(MysqlContent.PATH_SEPARATOR);
        } else {
            Asserts.throwException("No database selected");
        }
        path.append(name);
        invokeCommandBuilder.addPath(path.toString());
        InvokeCommand build = invokeCommandBuilder.build();
        NodeInvokeResult nodeInvokeResult = gatewaySdkService.invokeCallNode(build);
        nodeInvokeResult.setSourcePlan(this);
        if (!haveResult) {
            nodeInvokeResult.setResult(new ArrayList<>());
        }
        return nodeInvokeResult;
    }
}
