package team.opentech.usher.mqtt.pojo.resp;

import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.enums.SessionPresentEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月07日 09时44分
 */
public class MqttConneckResponse extends AbstractMqttResponse {

    /**
     * session标识
     */
    private final SessionPresentEnum sessionPresent;

    public MqttConneckResponse(SessionPresentEnum sessionPresent) {
        this.sessionPresent = sessionPresent;
    }

    @Override
    protected byte[] toPlayLoad() {
        return null;
    }

    @Override
    protected byte[] toVariableHeader() {
        return new byte[]{0, sessionPresent.getCode()};
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.CONNACK;
    }
}
