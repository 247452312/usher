package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时01分
 */
public class MqttPubAckCommand extends AbstractMqttCommand {

    private Integer packetIdentifier;

    protected MqttPubAckCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        mqttServiceHandler.pubCck(team.opentech.usher.mqtt.handler.cqe.MqttPubAckCommand.build(packetIdentifier));
        return null;
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.packetIdentifier = loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {

    }
}
