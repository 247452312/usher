package top.uhyils.usher.mysql.pojo.plan;

import java.util.Map;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;

/**
 * 左连接执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 15时55分
 */
public abstract class LeftJoinSqlPlan extends JoinSqlPlan {

    protected LeftJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        super(headers, tree, leftPlanId, rightPlanId);
    }
}
