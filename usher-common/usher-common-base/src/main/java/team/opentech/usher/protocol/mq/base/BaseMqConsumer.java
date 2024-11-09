package team.opentech.usher.protocol.mq.base;

import org.apache.rocketmq.client.consumer.MQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;

import java.util.List;

/**
 * mq consumer标识
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月14日 15时32分
 */
public interface BaseMqConsumer {

    /**
     * 监听的topic
     *
     * @return
     */
    String topic();

    /**
     * 监听的tag
     *
     * @return
     */
    List<String> tags();

    /**
     * 消费者信息
     *
     * @return
     */
    String group();

    /**
     * 当前消费者是否有序消费
     *
     * @return
     */
    boolean isOrder();

    /**
     * 消费者接收到消息时的动作
     *
     * @param message
     */
    RocketMqMessageResEnum onMessage(byte[] message);

    /**
     * 设置pushConsumer
     *
     * @param mq
     */
    void setPushConsumer(MQPushConsumer mq);

    /**
     * 开启consumer
     */
    void start() throws MQClientException;
}
