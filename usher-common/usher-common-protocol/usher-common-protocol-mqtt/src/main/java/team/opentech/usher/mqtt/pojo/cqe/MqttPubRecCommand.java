package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.resp.MqttPubRecResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttPubRelResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时15分
 */
public class MqttPubRecCommand extends AbstractMqttCommand {

    private Integer packetIdentifier;

    public MqttPubRecCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        MqttPubRecResponse mqttPubRecResponse = mqttServiceHandler.pubRec(team.opentech.usher.mqtt.handler.cqe.MqttPubRecCommand.build(packetIdentifier));
        return new MqttPubRelResponse(packetIdentifier).toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.packetIdentifier = loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
    }
}
