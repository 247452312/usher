package team.opentech.usher.mqtt.pojo.resp;

import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时37分
 */
public class MqttPingResponse extends AbstractMqttResponse {

    @Override
    protected byte[] toPlayLoad() {
        return null;
    }

    @Override
    protected byte[] toVariableHeader() {
        return null;
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.PINGRESP;
    }
}
