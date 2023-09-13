package team.opentech.usher.mqtt.handler.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时05分
 */
public class MqttPubAckCommand extends AbstractCommand {

    /**
     * 包序号
     */
    private Integer packetIdentifier;

    /**
     * 快捷创建
     */
    public static MqttPubAckCommand build(Integer packetIdentifier) {
        MqttPubAckCommand build = new MqttPubAckCommand();
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
