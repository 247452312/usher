package top.uhyils.usher.protocol.register;

import top.uhyils.usher.pojo.cqe.event.CheckAndAddRelegationEvent;
import top.uhyils.usher.protocol.register.base.Register;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时08分
 */
public interface RelegationRegister extends Register {

    /**
     * 检查并插入接口降级权重表
     *
     * @param event
     */
    void checkAndAddRelegation(CheckAndAddRelegationEvent event);
}
