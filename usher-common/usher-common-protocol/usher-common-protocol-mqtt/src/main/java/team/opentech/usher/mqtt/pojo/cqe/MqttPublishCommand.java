package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.resp.MqttPublishResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.netty.callback.impl.MqttCallbackImpl;
import team.opentech.usher.mqtt.pojo.resp.MqttPubAckResponse;
import team.opentech.usher.mqtt.pojo.resp.MqttPubRecResponse;
import team.opentech.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月12日 09时05分
 */
public class MqttPublishCommand extends AbstractMqttCommand {

    /**
     * publish的qos
     * 0 - At most once delivery(最多交付一次)
     * 1 - At least once delivery(至少交付一次)
     * 2 - Exactly once delivery(只交付一次)
     */
    private Integer qos;

    /**
     * topic
     */
    private String topic;

    /**
     * 包长度
     */
    private Integer packetIdentifier;

    /**
     * 内容
     */
    private String body;

    public MqttPublishCommand(byte[] bytes) {
        super(bytes);
    }


    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        MqttPublishResponse response = mqttServiceHandler.publish(team.opentech.usher.mqtt.handler.cqe.MqttPublishCommand.build(qos, topic, body, new MqttCallbackImpl()));
        if (Objects.equals(qos, 0)) {
            return null;
        } else if (Objects.equals(qos, 1)) {
            return new MqttPubAckResponse(qos, topic, packetIdentifier, response.getSuccess()).toBytes();
        } else if (Objects.equals(qos, 2)) {
            return new MqttPubRecResponse(packetIdentifier).toBytes();
        } else {
            Asserts.throwException("错误,不存在的qos:{}", qos);
            return null;
        }
    }

    @Override
    protected void loadFixed(ByteBuf buf) {
        byte firstByte = buf.readByte();
        int i = firstByte & 0x1111;
        this.qos = i >> 1 & 0x11;
        // 获取长度
        this.length = loadMqttLength(buf);
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.topic = loadLastStringItem(buf);
        this.packetIdentifier = loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
        byte[] dst = new byte[buf.writerIndex() - buf.readerIndex()];
        buf.readBytes(dst);
        this.body = new String(dst, StandardCharsets.UTF_8);

    }
}
