package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时23分
 */
public class MqttPubCompCommand extends AbstractMqttCommand {

    private Integer packetIdentifier;

    public MqttPubCompCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        mqttServiceHandler.pubComp(team.opentech.usher.mqtt.handler.cqe.MqttPubCompCommand.build(packetIdentifier));
        return null;
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
        this.packetIdentifier = this.loadLastInt(buf);
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
    }
}
