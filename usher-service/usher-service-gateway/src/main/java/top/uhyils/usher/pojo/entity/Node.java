package top.uhyils.usher.pojo.entity;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.exception.AssertException;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.plan.MysqlPlan;
import top.uhyils.usher.mysql.plan.PlanInvoker;
import top.uhyils.usher.mysql.pojo.DTO.NodeInvokeResult;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import top.uhyils.usher.mysql.util.PlanUtil;
import top.uhyils.usher.pojo.DO.NodeDO;
import top.uhyils.usher.repository.NodeRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.util.LogUtil;

/**
 * 转换节点表(Node)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class Node extends AbstractDataNode<NodeDO> {

    /**
     * 此节点对应的执行计划
     */
    private List<MysqlPlan> plans;


    @Default
    public Node(NodeDO data) {
        super(data);
    }

    public Node(Long id) {
        super(id, new NodeDO());
    }

    @NotNull
    private static MysqlPlan makeNodePlan(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository, BlockQuerySelectSqlPlan plan, String sql) {
        BlockQuerySelectSqlPlan selectSqlPlan = plan;
        SqlTableSourceBinaryTreeInfo sqlTableSourceBinaryTreeInfo = selectSqlPlan.toTable();
        SQLExprTableSource tableSource = sqlTableSourceBinaryTreeInfo.getTableSource();

        SQLName name = tableSource.getName();

        MysqlTcpLink mysqlTcpLink = MysqlContent.MYSQL_TCP_INFO.get();
        String owner = null;
        if (name instanceof SQLPropertyExpr) {
            SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr) name;
            owner = sqlPropertyExpr.getOwner() != null ? sqlPropertyExpr.getOwnernName() : mysqlTcpLink.getDatabase();
        } else if (name instanceof SQLIdentifierExpr) {
            owner = mysqlTcpLink.getDatabase();
        }

        AbstractDataNode abstractDataNode = nodeRepository.findNodeOrProvider(owner, name.getSimpleName());
        abstractDataNode.fill(nodeRepository, providerInterfaceRepository);
        MysqlPlan nodeInvokePlan = new MysqlNodeInvokePlan(abstractDataNode, selectSqlPlan, sql);
        return nodeInvokePlan;
    }

    @Override
    public String databaseName() {
        return toDataAndValidate().getDatabase();
    }

    @Override
    public String tableName() {
        return toDataAndValidate().getTableName();
    }

    /**
     * 填充子节点
     *
     * @param nodeRepository
     */
    @Override
    public void fill(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository) {
        String sql = toDataAndValidate().getSql();
        // 解析sql为执行计划
        List<MysqlPlan> mysqlPlans = PlanUtil.analysisSqlToPlan(sql);

        List<MysqlPlan> result = new ArrayList<>();
        // 将blockSql 执行计划 转换为node执行计划
        for (MysqlPlan plan : mysqlPlans) {
            if (plan instanceof BlockQuerySelectSqlPlan) {
                MysqlPlan nodeInvokePlan = makeNodePlan(nodeRepository, providerInterfaceRepository, (BlockQuerySelectSqlPlan) plan, sql);
                result.add(nodeInvokePlan);
                continue;
            }
            result.add(plan);
        }
        this.plans = result;
    }

    @Override
    public NodeInvokeResult getResult(Map<String, String> header, Map<String, Object> params) {
        NodeInvokeResult execute = null;
        try {
            execute = PlanInvoker.execute(plans, params);
        } catch (AssertException e) {
            LogUtil.error(e);
        }
        return execute;
    }
}
