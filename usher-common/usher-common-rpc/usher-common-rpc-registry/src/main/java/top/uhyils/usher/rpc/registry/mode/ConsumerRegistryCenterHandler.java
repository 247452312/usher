package top.uhyils.usher.rpc.registry.mode;

import java.util.function.Consumer;
import top.uhyils.usher.rpc.registry.pojo.event.RegistryEvent;

/**
 * 消费者 注册中心句柄
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年04月23日 15时19分
 */
public interface ConsumerRegistryCenterHandler extends RegistryCenterHandler {


    /**
     * 注册事件回调
     *
     * @param function
     */
    void registerEvent(Consumer<RegistryEvent> function);

}
