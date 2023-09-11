package team.opentech.usher.mqtt.exception;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 12时56分
 */
public class MqttLengthException extends MqttException {

    public MqttLengthException(long length) {
        super("mqtt长度错误,length: " + length);
    }
}
