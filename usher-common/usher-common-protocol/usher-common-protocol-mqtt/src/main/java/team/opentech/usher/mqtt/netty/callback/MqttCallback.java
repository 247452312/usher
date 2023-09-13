package team.opentech.usher.mqtt.netty.callback;

/**
 * mqtt的回调
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 09时32分
 */
public interface MqttCallback {

    /**
     * 发送消息到client
     *
     * @param clientIdentifier 客户端唯一标识
     * @param topic            topic
     * @param packetIdentifier 唯一标示
     * @param qos              qos
     * @param msg              消息体
     */
    void publishToClient(String clientIdentifier, String topic, Integer packetIdentifier, Integer qos, String msg);


}
