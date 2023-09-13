package team.opentech.usher.mqtt.pojo.resp;

import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.util.MqttUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时58分
 */
public class MqttUnSubAckResponse extends AbstractMqttResponse {

    private final Integer packetIdentifier;

    public MqttUnSubAckResponse(Integer packetIdentifier) {
        this.packetIdentifier = packetIdentifier;
    }

    @Override
    protected byte[] toPlayLoad() {
        return null;
    }

    @Override
    protected byte[] toVariableHeader() {
        return MqttUtil.toIntBytes(packetIdentifier);
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.UNSUBACK;
    }
}
