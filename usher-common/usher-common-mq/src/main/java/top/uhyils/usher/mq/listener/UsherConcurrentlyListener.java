package top.uhyils.usher.mq.listener;

import java.util.List;
import java.util.Objects;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import top.uhyils.usher.mq.core.BaseMqConsumer;
import top.uhyils.usher.mq.core.RocketMqMessageResEnum;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月22日 12时21分
 */
public class UsherConcurrentlyListener implements MessageListenerConcurrently {

    private final BaseMqConsumer consumer;

    public UsherConcurrentlyListener(BaseMqConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        try {
            boolean success = msgs.stream().map(t -> consumer.onMessage(t.getBody())).allMatch(rocketMqMessageResEnum -> Objects.equals(rocketMqMessageResEnum, RocketMqMessageResEnum.SUCCESS));
            return success ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
        } catch (Exception e) {
            LogUtil.error(this, e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
