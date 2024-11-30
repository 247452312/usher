package top.uhyils.usher.mysql.plan;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOperator;
import com.alibaba.druid.sql.ast.expr.SQLMethodInvokeExpr;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;
import top.uhyils.usher.mysql.pojo.plan.AbstractResultMappingPlan;
import top.uhyils.usher.mysql.pojo.plan.BlockQuerySelectSqlPlan;
import top.uhyils.usher.mysql.pojo.plan.InnerJoinSqlPlan;
import top.uhyils.usher.mysql.pojo.plan.LeftJoinSqlPlan;
import top.uhyils.usher.mysql.pojo.plan.MethodInvokePlan;
import top.uhyils.usher.mysql.pojo.plan.RightJoinSqlPlan;
import top.uhyils.usher.mysql.pojo.sql.MySQLSelectItem;

/**
 * 执行计划工厂(集成spring,使用方应该实现此工厂,并使用bean的形式结合到spring中)
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 15时54分
 */
public interface PlanFactory {

    /**
     * 创建一个简单sql执行计划
     *
     * @return
     */
    BlockQuerySelectSqlPlan buildBlockQuerySelectSqlPlan(SqlTableSourceBinaryTreeInfo froms, Map<String, String> headers, Map<String, Object> params);


    /**
     * 创建一个全连接执行计划
     *
     * @return
     */
    InnerJoinSqlPlan buildInnerJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId);


    /**
     * 创建一个左连接执行计划
     *
     * @return
     */
    LeftJoinSqlPlan buildLeftJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId);


    /**
     * 创建一个执行方法的执行计划
     *
     * @param headers
     * @param resultIndex 结果坐标
     * @param methodName  方法名称
     * @param arguments   参数
     * @param asName      别名
     *
     * @return
     */
    MethodInvokePlan buildMethodInvokePlan(Map<String, String> headers, Integer resultIndex, String methodName, List<SQLExpr> arguments, SQLMethodInvokeExpr asName);


    /**
     * 创建一个结果映射执行计划
     *
     * @return
     */
    AbstractResultMappingPlan buildResultMappingPlan(Map<String, String> headers, MysqlPlan lastMainPlan, List<MySQLSelectItem> selectList);


    /**
     * 创建一个右连接执行计划
     *
     * @return
     */
    RightJoinSqlPlan buildRightJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId);


    /**
     * 创建一个union sql执行计划
     *
     * @param planIds
     * @param headers
     *
     * @return
     */
    MysqlPlan buildUnionSelectSqlPlan(Map<String, String> headers, List<Long> planIds);

    /**
     * 创建一个表达式执行计划
     *
     * @param leftExpr  表达式左边
     * @param operator  表达式符号
     * @param rightExpr 表达式右边
     *
     * @return
     */
    MysqlPlan buildBinarySqlPlan(Map<String, String> headers, SQLExpr leftExpr, SQLBinaryOperator operator, SQLExpr rightExpr);

    /**
     * 创建一个use执行计划
     *
     * @return
     */
    MysqlPlan buildUsePlan(String database, Map<String, String> headers);

}
