package top.uhyils.usher.bus;

import java.util.List;
import top.uhyils.usher.mq.core.BaseMqConsumer;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseParentEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月29日 21时51分
 */
public interface BusInterface extends BaseMqConsumer {

    /**
     * 事件路由名称
     */
    String BUS_EVENT_TOPIC_NAME = "BUS_EVENT_TOPIC_NAME";

    /**
     * 事件GROUP
     */
    String BUS_EVENT_GROUP_NAME = "BUS_EVENT_GROUP";

    /**
     * 事件队列
     */
    String BUS_EVENT_TAG_NAME = "BUS_EVENT_TAG_NAME";

    /**
     * 提交事件
     *
     * @param event
     */
    void commit(BaseParentEvent event);

    /**
     * 批量提交事件
     *
     * @param events
     */
    void commit(BaseParentEvent... events);

    /**
     * 提交事务消息
     *
     * @param events
     */
    void commitAndPushWithTransactional(BaseParentEvent... events);

    /**
     * 提交事务消息
     *
     * @param events
     */
    void commitAndPushWithTransactional(BaseParentEvent events);


    /**
     * 清空事件
     */
    void cleanCommitEvent();

    /**
     * 发布事件
     */
    void pushEvent();

    /**
     * 批量提交并发送事件 (不发送存储事件)
     *
     * @param baseEvents
     */
    void commitAndPush(BaseParentEvent... baseEvents);

    /**
     * 提交并发送事件 (不发送存储事件)
     *
     * @param baseEvents
     */
    void commitAndPush(BaseParentEvent baseEvents);

    /**
     * 异步发布事件
     */
    void asyncPushEvent();

    /**
     * 批量异步提交并发送事件 (不发送存储事件)
     *
     * @param baseEvents
     */
    void asyncCommitAndPush(BaseParentEvent... baseEvents);

    /**
     * 异步提交并发送事件 (不发送存储事件)
     *
     * @param baseEvent
     */
    void asyncCommitAndPush(BaseParentEvent baseEvent);

    /**
     * 移除还没有发布的指定事件(包括子类)
     *
     * @param baseEventClass
     *
     * @return
     */
    List<BaseEvent> remove(Class<? extends BaseEvent> baseEventClass);

    /**
     * 精准移除还没有发布的指定事件(不包括子类)
     *
     * @param baseEventClass
     *
     * @return
     */
    List<BaseEvent> preciseRemove(Class<? extends BaseEvent> baseEventClass);

    /**
     * 精准获取未发布的事件(不包括子类)
     *
     * @param baseEventClass
     *
     * @return
     */
    List<BaseEvent> preciseGet(Class<? extends BaseEvent> baseEventClass);

    /**
     * 获取未发布的事件(包括子类)
     *
     * @param baseEventClass
     *
     * @return
     */
    List<BaseEvent> get(Class<? extends BaseEvent> baseEventClass);
}
