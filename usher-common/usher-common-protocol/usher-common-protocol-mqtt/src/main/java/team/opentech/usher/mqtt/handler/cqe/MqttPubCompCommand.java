package team.opentech.usher.mqtt.handler.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时24分
 */
public class MqttPubCompCommand extends AbstractCommand {


    private Integer packetIdentifier;

    /**
     * 快捷创建
     */
    public static MqttPubCompCommand build(Integer packetIdentifier) {
        MqttPubCompCommand build = new MqttPubCompCommand();
        build.setPacketIdentifier(packetIdentifier);
        return build;
    }

    public Integer getPacketIdentifier() {
        return packetIdentifier;
    }

    public void setPacketIdentifier(Integer packetIdentifier) {
        this.packetIdentifier = packetIdentifier;
    }
}
