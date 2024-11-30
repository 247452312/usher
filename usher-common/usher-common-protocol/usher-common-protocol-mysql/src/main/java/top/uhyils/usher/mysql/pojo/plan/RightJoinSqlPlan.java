package top.uhyils.usher.mysql.pojo.plan;

import java.util.Map;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;

/**
 * 右连接执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 15时56分
 */
public abstract class RightJoinSqlPlan extends JoinSqlPlan {

    protected RightJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        super(headers, tree, leftPlanId, rightPlanId);
    }
}
