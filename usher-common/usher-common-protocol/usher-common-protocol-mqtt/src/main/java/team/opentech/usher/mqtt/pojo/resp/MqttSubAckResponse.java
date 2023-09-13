package team.opentech.usher.mqtt.pojo.resp;

import java.util.List;
import java.util.Objects;
import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.mqtt.enums.MqttCommandTypeEnum;
import team.opentech.usher.mqtt.handler.resp.MqttSubscribeResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 20时08分
 */
public class MqttSubAckResponse extends AbstractMqttResponse {

    private Integer packetIdentifier;

    private List<MqttSubscribeResponse> resp;

    public MqttSubAckResponse(Integer packetIdentifier, List<MqttSubscribeResponse> resp) {
        super();
        this.packetIdentifier = packetIdentifier;
        this.resp = resp;
    }

    @Override
    protected byte[] toPlayLoad() {
        byte[] result = new byte[resp.size()];
        for (int i = 0; i < resp.size(); i++) {
            MqttSubscribeResponse respItem = resp.get(i);
            if (Objects.equals(Boolean.FALSE, respItem.getSuccess())) {
                result[i] = (byte) 128;
                continue;
            }
            switch (respItem.getQos()) {
                case 0:
                    result[i] = 0;
                    break;
                case 1:
                    result[i] = 1;
                    break;
                case 2:
                    result[i] = 2;
                    break;
                default:
            }
        }
        return result;
    }

    @NotNull
    @Override
    protected byte[] toVariableHeader() {
        return new byte[]{(byte) (packetIdentifier >> 8), (byte) (packetIdentifier & ((2 << 9) - 1))};
    }

    @Override
    protected MqttCommandTypeEnum getType() {
        return MqttCommandTypeEnum.SUBACK;
    }
}
