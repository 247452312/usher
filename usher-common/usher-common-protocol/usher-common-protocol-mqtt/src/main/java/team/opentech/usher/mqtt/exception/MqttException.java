package team.opentech.usher.mqtt.exception;

/**
 * mqtt的异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 12时56分
 */
public class MqttException extends RuntimeException {

    public MqttException() {
        super();
    }

    public MqttException(String message) {
        super(message);
    }

    public MqttException(String message, Throwable cause) {
        super(message, cause);
    }

    public MqttException(Throwable cause) {
        super(cause);
    }

    protected MqttException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
