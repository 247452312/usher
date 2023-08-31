package team.opentech.usher.mysql.plan;

import java.util.Map;
import team.opentech.usher.mysql.enums.MysqlPlanTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.NodeInvokeResult;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月29日 08时40分
 */
public interface MysqlPlan {

    /**
     * 执行执行计划
     *
     * @return
     */
    NodeInvokeResult invoke();

    /**
     * 补全执行计划参数
     *
     * @param planArgs 计划参数<执行计划id,执行计划结果>
     */
    void complete(Map<Long, NodeInvokeResult> planArgs);

    /**
     * 获取此执行计划的类型
     *
     * @return
     */
    MysqlPlanTypeEnum type();

    /**
     * 获取此plan唯一标示
     *
     * @return
     */
    long getId();


}
