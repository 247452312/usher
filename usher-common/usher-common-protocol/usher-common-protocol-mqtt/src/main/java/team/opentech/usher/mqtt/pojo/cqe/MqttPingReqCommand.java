package team.opentech.usher.mqtt.pojo.cqe;

import io.netty.buffer.ByteBuf;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.mqtt.pojo.resp.MqttPingResponse;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时36分
 */
public class MqttPingReqCommand extends AbstractMqttCommand {

    public MqttPingReqCommand(byte[] bytes) {
        super(bytes);
    }

    @Override
    public byte[] invoke(MqttNettyHandler nettyHandler, MqttServiceHandler mqttServiceHandler) {
        return new MqttPingResponse().toBytes();
    }

    @Override
    protected void loadVariableHeader(ByteBuf buf) {
    }

    @Override
    protected void loadPlayLoad(ByteBuf buf) {
    }
}
