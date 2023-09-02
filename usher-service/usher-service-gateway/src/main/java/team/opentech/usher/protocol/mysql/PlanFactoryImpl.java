package team.opentech.usher.protocol.mysql;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import team.opentech.usher.mysql.plan.MysqlPlan;
import team.opentech.usher.mysql.plan.PlanFactory;
import team.opentech.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import team.opentech.usher.mysql.pojo.plan.AbstractResultMappingPlan;
import team.opentech.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import team.opentech.usher.mysql.pojo.plan.InnerJoinSqlPlan;
import team.opentech.usher.mysql.pojo.plan.LeftJoinSqlPlan;
import team.opentech.usher.mysql.pojo.plan.MethodInvokePlan;
import team.opentech.usher.mysql.pojo.plan.RightJoinSqlPlan;
import team.opentech.usher.mysql.pojo.plan.impl.BinarySqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.BlockQuerySelectSqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.InnerJoinSqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.LeftJoinSqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.MethodInvokePlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.ResultMappingPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.RightJoinSqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.UnionSqlPlanImpl;
import team.opentech.usher.mysql.pojo.plan.impl.UseSqlPlanImpl;
import team.opentech.usher.mysql.pojo.sql.MySQLSelectItem;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 16时13分
 */
@Component
public class PlanFactoryImpl implements PlanFactory {

    @Override
    public BlockQuerySelectSqlPlan buildBlockQuerySelectSqlPlan(SqlTableSourceBinaryTreeInfo froms, Map<String, String> headers, Map<String, Object> params) {
        return new BlockQuerySelectSqlPlanImpl(froms, headers, params);
    }

    @Override
    public InnerJoinSqlPlan buildInnerJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        return new InnerJoinSqlPlanImpl(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public LeftJoinSqlPlan buildLeftJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
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
    public RightJoinSqlPlan buildRightJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        return new RightJoinSqlPlanImpl(headers, tree, leftPlanId, rightPlanId);
    }

    @Override
    public MysqlPlan buildUnionSelectSqlPlan(Map<String, String> headers, List<Long> planIds) {
        return new UnionSqlPlanImpl(headers, planIds);
    }

    @Override
    public MysqlPlan buildBinarySqlPlan(Map<String, String> headers, SQLExpr leftExpr, SQLBinaryOperator operator, SQLExpr rightExpr) {
        return new BinarySqlPlanImpl(headers, leftExpr, operator, rightExpr);
    }

    @Override
    public MysqlPlan buildUsePlan(String database, Map<String, String> headers) {
        return new UseSqlPlanImpl(database, headers, new HashMap<>());
    }


}
