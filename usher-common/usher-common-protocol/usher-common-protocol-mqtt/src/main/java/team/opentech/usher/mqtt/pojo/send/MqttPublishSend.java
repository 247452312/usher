package team.opentech.usher.mqtt.pojo.send;

import java.nio.charset.StandardCharsets;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.util.MqttUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 11时16分
 */
public class MqttPublishSend extends AbstractMqttSend {

    private final Integer qos;

    private final String topic;

    private final Integer packetIdentifier;

    private final String msg;

    public MqttPublishSend(Integer qos, String topic, Integer packetIdentifier, String msg) {
        this.qos = qos;
        this.topic = topic;
        this.packetIdentifier = packetIdentifier;
        this.msg = msg;
    }

    @Override
    protected byte[] toPlayLoad() {
        return msg.getBytes(StandardCharsets.UTF_8);
    }

    @NotNull
    @Override
    protected byte[] toVariableHeader() {
        byte[] topicBytes = MqttUtil.toLengthAndStr(topic);
        byte[] packetIdentifierBytes = MqttUtil.toIntBytes(packetIdentifier);
        return MqttUtil.mergeBytes(topicBytes, packetIdentifierBytes);
    }

    @Override
    protected byte firstFlags() {
        return (byte) (3 << 4 & (qos << 1));
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.PUBLISH;
    }
}
