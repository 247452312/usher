package team.opentech.usher.mq.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.consumer.MQConsumer;
import team.opentech.usher.mq.filter.MQConsumerFilter;
import team.opentech.usher.mq.filter.MQMessageFilter;
import team.opentech.usher.mq.pojo.BackInfo;
import team.opentech.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 18时29分
 */
public abstract class AbstractMQClient implements MQClient {

    /**
     * 缓存consumer
     */
    protected Map<String, List<MQConsumer>> consumerListMap = new HashMap<>();

    /**
     * callback的临时存储的地方
     */
    protected Map<Long, MQCallBack> callBackMap = new ConcurrentHashMap<>();

    private List<MQConsumerFilter> consumerFilters = SpringUtil.getBeans(MQConsumerFilter.class);

    private List<MQMessageFilter> messageFilters = SpringUtil.getBeans(MQMessageFilter.class);

    @Override
    public <T extends MQConsumer> T addConsumer(String topic, Supplier<T> consumerFunction) {

        T consumer = consumerFunction.get();
        for (MQConsumerFilter filter : consumerFilters) {
            consumer = filter.filter(consumer);
        }

        if (!consumerListMap.containsKey(topic)) {
            consumerListMap.put(topic, new ArrayList<>());
        }
        consumerListMap.get(topic).add(consumer);
        doAddConsumer(consumer);
        return consumer;
    }

    @Override
    public <T extends MQConsumer> T addNoFilterConsumer(String topic, Supplier<T> consumerFunction) {
        T consumer = consumerFunction.get();
        if (!consumerListMap.containsKey(topic)) {
            consumerListMap.put(topic, new ArrayList<>());
        }
        consumerListMap.get(topic).add(consumer);
        doAddConsumer(consumer);
        return consumer;
    }

    @Override
    public void send(MQMessage message) {
        message = doFilter(message);
        sendOriginal(message);
    }

    @Override
    public void send(MQMessage message, MQCallBack callBack) {
        message = doFilter(message);
        sendOriginal(message, callBack);
    }

    @Override
    public void sendOriginal(MQMessage message, MQCallBack callBack) {
        callBackMap.put(message.id(), callBack);
        sendOriginal(message);
    }

    /**
     * 处理回调
     */
    protected void dealCallBack(Long id, BackInfo backInfo) {
        MQCallBack mqCallBack = callBackMap.get(id);
        if (mqCallBack != null) {
            mqCallBack.invoke(backInfo);
            callBackMap.remove(id);
        }

    }

    /**
     * 添加consumer
     *
     * @param consumer
     */
    protected abstract void doAddConsumer(MQConsumer consumer);

    /**
     * 包装一层
     *
     * @param message
     */
    private MQMessage doFilter(MQMessage message) {
        for (MQMessageFilter messageFilter : messageFilters) {
            message = messageFilter.invoke(message);
        }
        return message;
    }


}
