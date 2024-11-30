package top.uhyils.usher.mq.listener;

import java.util.List;
import java.util.Objects;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import top.uhyils.usher.mq.core.BaseMqConsumer;
import top.uhyils.usher.mq.core.RocketMqMessageResEnum;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月22日 12时20分
 */
public class UsherOrderlyListener implements MessageListenerOrderly {

    private final BaseMqConsumer consumer;

    public UsherOrderlyListener(BaseMqConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
        try {
            boolean b = msgs.stream().map(t -> consumer.onMessage(t.getBody())).allMatch(rocketMqMessageResEnum -> Objects.equals(rocketMqMessageResEnum, RocketMqMessageResEnum.SUCCESS));
            return b ? ConsumeOrderlyStatus.SUCCESS : ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        } catch (Exception e) {
            LogUtil.error(this, e);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
    }
}
