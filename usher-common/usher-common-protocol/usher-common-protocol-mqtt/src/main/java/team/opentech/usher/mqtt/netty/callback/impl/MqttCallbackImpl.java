package team.opentech.usher.mqtt.netty.callback.impl;

import team.opentech.usher.mqtt.context.MqttContext;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.netty.callback.MqttCallback;
import team.opentech.usher.mqtt.pojo.send.MqttPublishSend;
import team.opentech.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 09时41分
 */
public class MqttCallbackImpl implements MqttCallback {

    @Override
    public void publishToClient(String clientIdentifier, String topic, Integer packetIdentifier, Integer qos, String msg) {
        MqttNettyHandler nettyHandler = MqttContext.findNettyHandler(clientIdentifier);
        Asserts.assertTrue(nettyHandler != null, "{} 未订阅", clientIdentifier);
        MqttPublishSend mqttPublishSend = new MqttPublishSend(qos, topic, packetIdentifier, msg);
        byte[] bytes = mqttPublishSend.toBytes();
        nettyHandler.send(bytes);
    }
}
