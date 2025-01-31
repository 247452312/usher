package team.opentech.usher.protocol.mq;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import team.opentech.usher.annotation.MyMq;
import team.opentech.usher.mq.content.RabbitMqContent;
import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.protocol.mq.base.AbstractMqConsumer;
import team.opentech.usher.service.LogMonitorService;
import team.opentech.usher.util.LogUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.context.ApplicationContext;

/**
 * 监听JVM启动消息
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 11时33分
 */
@MyMq(topic = RabbitMqContent.JVM_START_QUEUE_NAME, tags = {RabbitMqContent.JVM_START_QUEUE_NAME}, group = "监听JVM启动消息")
public class RabbitJvmStartInfoConsumer extends AbstractMqConsumer {

    private LogMonitorService logMonitorService;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel            the channel to which this consumer is attached
     * @param applicationContext
     */
    public RabbitJvmStartInfoConsumer(Channel channel, ApplicationContext applicationContext) {
        super(channel);
        logMonitorService = applicationContext.getBean(LogMonitorService.class);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String text = new String(body, StandardCharsets.UTF_8);
        JvmStartInfoCommand jvmStartInfo = JSONObject.parseObject(text, JvmStartInfoCommand.class);
        LogUtil.info(this, "接收到JVM启动信息");
        LogUtil.info(this, text);
        logMonitorService.receiveJvmStartInfo(jvmStartInfo);

        // 获取tag(队列中的唯一标示)
        long deliveryTag = envelope.getDeliveryTag();
        // 确认 false为不批量确认
        getChannel().basicAck(deliveryTag, false);
    }
}
