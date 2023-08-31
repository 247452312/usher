package team.opentech.usher.mysql.pojo.plan.impl;

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
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.handler.MysqlServiceHandler;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import team.opentech.usher.mysql.pojo.cqe.InvokeCommandBuilder;
import team.opentech.usher.mysql.pojo.cqe.MysqlInvokeCommand;
import team.opentech.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
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
    private final MysqlServiceHandler mysqlSdkService;


    public BlockQuerySelectSqlPlanImpl(SqlTableSourceBinaryTreeInfo froms, Map<String, String> headers, Map<String, Object> params) {
        super(froms, headers, params);
        this.mysqlSdkService = SpringUtil.getBean(MysqlServiceHandler.class);
    }

    @Override
    public NodeInvokeResult invoke() {
        InvokeCommandBuilder invokeCommandBuilder = new InvokeCommandBuilder();
        invokeCommandBuilder.addArgs(params);
        invokeCommandBuilder.addHeader(headers);
        SQLExprTableSource tableSource = froms.getTableSource();
        invokeCommandBuilder.addAlias(tableSource.getAlias());
        SQLPropertyExpr expr = (SQLPropertyExpr) tableSource.getExpr();
        String owner = expr.getOwnernName();
        String tableName = expr.getName();
        if (tableName.startsWith("&")) {
            Long resultIndex = Long.parseLong(tableName.substring(1));
            return lastAllPlanResult.get(resultIndex);
        }
        List<SQLBinaryOpExpr> where = froms.getWhere();
        Map<String, Object> whereParams = new HashMap<>();
        boolean haveResult = true;
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
        String database = MysqlContent.MYSQL_TCP_INFO.get().getDatabase();
        if (owner != null) {
            invokeCommandBuilder.fillDatabase(owner);
        } else if (StringUtil.isNotEmpty(database)) {
            invokeCommandBuilder.fillDatabase(database);
        } else {
            Asserts.throwException("No database selected");
        }
        invokeCommandBuilder.fillTable(tableName);
        MysqlInvokeCommand build = invokeCommandBuilder.build();
        NodeInvokeResult nodeInvokeResult = mysqlSdkService.invokeSingleQuerySql(build);
        nodeInvokeResult.setSourcePlan(this);
        if (!haveResult) {
            nodeInvokeResult.setResult(new ArrayList<>());
        }
        return nodeInvokeResult;
    }
}
