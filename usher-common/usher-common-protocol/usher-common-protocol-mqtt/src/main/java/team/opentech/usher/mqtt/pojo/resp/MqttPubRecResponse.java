package team.opentech.usher.mqtt.pojo.resp;

import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.util.MqttUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 13时58分
 */
public class MqttPubRecResponse extends AbstractMqttResponse {

    private final Integer packetIdentifier;

    public MqttPubRecResponse(Integer packetIdentifier) {
        this.packetIdentifier = packetIdentifier;
    }

    @Override
    protected byte[] toPlayLoad() {
        return null;
    }

    @NotNull
    @Override
    protected byte[] toVariableHeader() {
        return MqttUtil.toIntBytes(packetIdentifier);
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.PUBREC;
    }
}
