package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.handler.cqe.MqttLogoutCommand;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时39分
 */
public class MqttDisConnectCommand extends AbstractMqttCommand {

    public MqttDisConnectCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        nettyHandler.logout();
        mqttServiceHandler.logout(MqttLogoutCommand.build(nettyHandler.clientIdentifier()));
        return null;
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {

    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {

    }
}
