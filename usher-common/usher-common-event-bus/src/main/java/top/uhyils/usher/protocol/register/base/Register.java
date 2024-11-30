package top.uhyils.usher.protocol.register.base;

import java.util.List;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;

/**
 * 事件注册者
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月19日 09时21分
 */
public interface Register {

    /**
     * 获取这个register监听的是哪一个事件
     *
     * @return
     */
    List<Class<? extends BaseEvent>> targetEvent();

    /**
     * 事件 coming!
     *
     * @param event
     */
    void onEvent(BaseEvent event);

}
