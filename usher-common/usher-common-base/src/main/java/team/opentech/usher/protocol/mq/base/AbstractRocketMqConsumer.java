package team.opentech.usher.protocol.mq.base;


import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;

import java.util.Objects;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月09日 11时14分
 */
public abstract class AbstractRocketMqConsumer extends AbstractMqConsumer {

    /**
     * 注册rocketMq消费者
     */
    public void registerMessageListener() {
        if (isOrder()) {
            mq.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
                boolean b = msgs.stream().map(t -> onMessage(t.getBody())).allMatch(rocketMqMessageResEnum -> Objects.equals(rocketMqMessageResEnum, RocketMqMessageResEnum.SUCCESS));
                if (b) {
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            });
        } else {
            mq.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                boolean b = msgs.stream().map(t -> onMessage(t.getBody())).allMatch(rocketMqMessageResEnum -> Objects.equals(rocketMqMessageResEnum, RocketMqMessageResEnum.SUCCESS));
                if (b) {
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
        }
    }
}
