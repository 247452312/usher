package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.resp.MqttPubRelResponse;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttPubCompResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时08分
 */
public class MqttPubRelCommand extends AbstractMqttCommand {

    private Integer packetIdentifier;

    public MqttPubRelCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        MqttPubRelResponse response = mqttServiceHandler.pubRel(team.opentech.usher.mqtt.handler.cqe.MqttPubRelCommand.build(packetIdentifier));
        return new MqttPubCompResponse(packetIdentifier).toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.packetIdentifier = loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {

    }
}
