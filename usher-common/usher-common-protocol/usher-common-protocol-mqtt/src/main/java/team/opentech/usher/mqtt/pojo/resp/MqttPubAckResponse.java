package team.opentech.usher.mqtt.pojo.resp;

import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月12日 09时34分
 */
public class MqttPubAckResponse extends AbstractMqttResponse {

    private Integer qos;

    private String topic;

    /**
     * 请求标识
     */
    private Integer packetIdentifier;

    /**
     * 是否发布消息成功
     */
    private Boolean success;

    public MqttPubAckResponse(Integer qos, String topic, Integer packetIdentifier, Boolean success) {
        super();
        this.qos = qos;
        this.topic = topic;
        this.packetIdentifier = packetIdentifier;
        this.success = success;
    }

    @Override
    protected byte[] toPlayLoad() {
        return null;
    }

    @NotNull
    @Override
    protected byte[] toVariableHeader() {
        return new byte[]{(byte) (packetIdentifier >> 4), (byte) (packetIdentifier & 0x1111)};
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.PUBACK;
    }
}
