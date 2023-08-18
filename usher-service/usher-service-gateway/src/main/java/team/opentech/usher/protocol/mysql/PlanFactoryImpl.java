package team.opentech.usher.protocol.mysql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import team.opentech.usher.plan.MysqlPlan;
import team.opentech.usher.plan.PlanFactory;
import team.opentech.usher.plan.pojo.MySQLSelectItem;
import team.opentech.usher.plan.pojo.SqlTableSourceBinaryTree;
import team.opentech.usher.plan.pojo.plan.AbstractResultMappingPlan;
import team.opentech.usher.plan.pojo.plan.BlockQuerySelectSqlPlan;
import team.opentech.usher.plan.pojo.plan.InnerJoinSqlPlan;
import team.opentech.usher.plan.pojo.plan.LeftJoinSqlPlan;
import team.opentech.usher.plan.pojo.plan.MethodInvokePlan;
import team.opentech.usher.plan.pojo.plan.RightJoinSqlPlan;
import team.opentech.usher.plan.pojo.plan.impl.InnerJoinSqlPlanImpl;
import team.opentech.usher.plan.pojo.plan.impl.LeftJoinSqlPlanImpl;
import team.opentech.usher.plan.pojo.plan.impl.MethodInvokePlanImpl;
import team.opentech.usher.plan.pojo.plan.impl.ResultMappingPlanImpl;
import team.opentech.usher.plan.pojo.plan.impl.RightJoinSqlPlanImpl;
import team.opentech.usher.plan.pojo.plan.impl.UnionSqlPlanImpl;
import team.opentech.usher.protocol.mysql.plan.BlockQuerySelectSqlPlanImpl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时13分
 */
@Component
public class PlanFactoryImpl implements PlanFactory {

    @Override
    public BlockQuerySelectSqlPlan buildBlockQuerySelectSqlPlan(SqlTableSourceBinaryTree froms, Map<String, String> headers, Map<String, Object> params) {
        return new BlockQuerySelectSqlPlanImpl(froms, headers, params);
    }

    @Override
    public InnerJoinSqlPlan buildInnerJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTree tree, Long leftPlanId, Long rightPlanId) {
        return new InnerJoinSqlPlanImpl(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public LeftJoinSqlPlan buildLeftJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTree tree, Long leftPlanId, Long rightPlanId) {
        return new LeftJoinSqlPlanImpl(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public MethodInvokePlan buildMethodInvokePlan(Map<String, String> headers, Integer resultIndex, String methodName, List<SQLExpr> arguments, SQLMethodInvokeExpr invokeExpr) {
        SQLObject parent = invokeExpr.getParent();
        String asName;
        if (parent instanceof SQLSelectItem) {
            asName = ((SQLSelectItem) parent).getAlias();
        } else {
            asName = invokeExpr.getOwner() != null ? invokeExpr.getOwner().toString() : null;
        }
        return new MethodInvokePlanImpl(headers, resultIndex, methodName, arguments, asName);
    }

    @Override
    public AbstractResultMappingPlan buildResultMappingPlan(Map<String, String> headers, MysqlPlan lastMainPlan, List<MySQLSelectItem> selectList) {
        return new ResultMappingPlanImpl(headers, lastMainPlan, selectList);
    }

    @Override
    public RightJoinSqlPlan buildRightJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTree tree, Long leftPlanId, Long rightPlanId) {
        return new RightJoinSqlPlanImpl(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public MysqlPlan buildUnionSelectSqlPlan(Map<String, String> headers, List<Long> planIds) {
        return new UnionSqlPlanImpl(headers, planIds);
    }
}
