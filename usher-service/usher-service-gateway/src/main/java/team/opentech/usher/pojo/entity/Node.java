package team.opentech.usher.pojo.entity;

import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.exception.AssertException;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.plan.MysqlPlan;
import team.opentech.usher.plan.PlanInvoker;
import team.opentech.usher.plan.pojo.SqlTableSourceBinaryTree;
import team.opentech.usher.plan.pojo.plan.BlockQuerySelectSqlPlan;
import team.opentech.usher.pojo.DO.NodeDO;
import team.opentech.usher.repository.NodeRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public String databaseName() {
        return toDataAndValidate().getDatabase();
    }

    @Override
    public String tableName() {
        return toDataAndValidate().getTableName();
    }

    @NotNull
    private static MysqlPlan makeNodePlan(NodeRepository nodeRepository, ProviderInterfaceRepository providerInterfaceRepository, BlockQuerySelectSqlPlan plan, String sql) {
        BlockQuerySelectSqlPlan selectSqlPlan = plan;
        SqlTableSourceBinaryTree sqlTableSourceBinaryTree = selectSqlPlan.toTable();
        SQLExprTableSource tableSource = sqlTableSourceBinaryTree.getTableSource();

        SQLName name = tableSource.getName();

        MysqlTcpInfo mysqlTcpInfo = MysqlContent.MYSQL_TCP_INFO.get();
        String owner = null;
        if (name instanceof SQLPropertyExpr) {
            SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr) name;
            owner = sqlPropertyExpr.getOwner() != null ? sqlPropertyExpr.getOwnernName() : mysqlTcpInfo.getDatabase();
        } else if (name instanceof SQLIdentifierExpr) {
            owner = mysqlTcpInfo.getDatabase();
        }

        AbstractDataNode abstractDataNode = nodeRepository.findNodeOrProvider(owner, name.getSimpleName());
        abstractDataNode.fill(nodeRepository, providerInterfaceRepository);
        MysqlPlan nodeInvokePlan = new MysqlNodeInvokePlan(abstractDataNode, selectSqlPlan, sql);
        return nodeInvokePlan;
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
        List<MysqlPlan> mysqlPlans = MysqlUtil.analysisSqlToPlan(sql);

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
            execute = new PlanInvoker(plans).execute();
        } catch (AssertException e) {
            LogUtil.error(e);
        }
        return execute;
    }
}