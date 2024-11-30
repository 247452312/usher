package top.uhyils.usher.mysql.pojo.plan;

import java.util.Map;
import top.uhyils.usher.mysql.pojo.SqlTableSourceBinaryTreeInfo;

/**
 * 全连接执行计划
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月26日 15时59分
 */
public abstract class InnerJoinSqlPlan extends JoinSqlPlan {


    protected InnerJoinSqlPlan(Map<String, String> headers, SqlTableSourceBinaryTreeInfo tree, Long leftPlanId, Long rightPlanId) {
        super(headers, tree, leftPlanId, rightPlanId);
    }
}
