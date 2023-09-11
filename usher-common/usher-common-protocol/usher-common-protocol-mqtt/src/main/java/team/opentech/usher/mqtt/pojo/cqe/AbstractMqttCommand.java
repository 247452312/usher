package team.opentech.usher.mqtt.pojo.cqe;

import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * mqtt请求模板
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月06日 11时16分
 */
public abstract class AbstractMqttCommand extends AbstractCommand {

    protected final byte[] bytes;

    /**
     * 长度
     */
    protected Long length;

    protected AbstractMqttCommand(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * 解析协议
     */
    public abstract void load();

    /**
     * 执行
     *
     * @param mqttServiceHandler
     *
     * @return
     */
    public abstract byte[] invoke(MqttServiceHandler mqttServiceHandler);

}
