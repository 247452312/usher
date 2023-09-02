package team.opentech.usher.mq.client;

import java.util.function.Supplier;
import team.opentech.usher.mq.MQMessage;
import team.opentech.usher.mq.consumer.MQConsumer;

/**
 * mq的通用client
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月02日 17时32分
 */
public interface MQClient {

    /**
     * 发送MQ消息
     *
     * @param message
     */
    void send(MQMessage message);

    /**
     * 有回调的请求
     *
     * @param message
     * @param callBack
     */
    void send(MQMessage message, MQCallBack callBack);

    /**
     * 推送信息到mq(无任何其他包装等消息)
     *
     * @param message 信息
     *
     * @return
     */
    Long sendOriginal(MQMessage message);

    /**
     * 推送信息到mq(无任何其他包装等消息)
     *
     * @param message 信息
     *
     * @return
     */
    void sendOriginal(MQMessage message, MQCallBack callBack);

    /**
     * 添加消费者
     */
    <T extends MQConsumer> T addConsumer(String topic, Supplier<T> consumerFunction);

    <T extends MQConsumer> T addNoFilterConsumer(String topic, Supplier<T> consumerFunction);
}
