package team.opentech.usher.mqtt.pojo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月13日 11时13分
 */
public interface MqttByteable {

    /**
     * 协议信息转协议体
     *
     * @return
     */
    byte[] toBytes();
}
