package team.opentech.usher.mqtt.handler.resp;

import java.io.Serializable;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 14时19分
 */
public class MqttPubRecResponse implements Serializable {

    /**
     * 包序号
     */
    private Integer packetIdentifier;

    public Integer getPacketIdentifier() {
        return packetIdentifier;
    }

    public void setPacketIdentifier(Integer packetIdentifier) {
        this.packetIdentifier = packetIdentifier;
    }
}
