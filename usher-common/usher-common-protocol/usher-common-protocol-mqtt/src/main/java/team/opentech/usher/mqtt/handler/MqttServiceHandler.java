package team.opentech.usher.mqtt.handler;

import team.opentech.usher.mqtt.handler.cqe.MqttLoginCommand;
import team.opentech.usher.mqtt.handler.resp.MqttLoginResponse;

/**
 * 需要子类实现的接口SPI
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月07日 09时24分
 */
public interface MqttServiceHandler {

    /**
     * mqtt连接登录
     */
    MqttLoginResponse login(MqttLoginCommand loginCommand);

}
