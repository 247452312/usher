package team.opentech.usher.mqtt.enums;

import java.util.Arrays;
import java.util.Objects;
import team.opentech.usher.util.Asserts;

/**
 * mqtt请求类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月04日 09时24分
 */
public enum MqttCommandTypeEnum {
    /**
     *
     */
    RESERVERD(0, "保留类型,不可用"),
    /**
     * client->server
     */
    CONNECT(1, "客户端连接到服务器"),
    /**
     * server->client
     */
    CONNACK(2, "链接确认"),
    /**
     * client<->server
     */
    PUBLISH(3, "发布消息"),
    /**
     * client<->server
     */
    PUBACK(4, "发布确认(Qos1)"),

    /**
     * client<->server
     */
    PUBREC(5, "消息已接收(Qos2 第一阶段)"),
    /**
     * client<->server
     */
    PUBREL(6, "消息释放(Qos2 第二阶段)"),

    /**
     * client<->server
     */
    PUBCOMP(7, "发布结束(Qos2 第三阶段)"),

    /**
     * client->server
     */
    SUBSCRIBE(8, "客户端订阅请求"),

    /**
     * server->client
     */
    SUBACK(9, "服务端订阅确认"),

    /**
     * client->server
     */
    UNSUBSCRIBE(10, "客户端取消订阅"),

    /**
     * client<->server
     */
    UNSUBACK(11, ""),

    /**
     *
     */
    PINGREQ(12, ""),

    /**
     *
     */
    PINGRESP(13, ""),

    /**
     *
     */
    DISCONNECT(14, ""),
    ;

    private final Integer code;

    private final String name;

    MqttCommandTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MqttCommandTypeEnum getByCode(Integer code) {
        return Arrays.stream(values()).filter(t -> Objects.equals(t.code, code)).findFirst().orElseThrow(() -> Asserts.makeException("未找到mqtt类型:{}", code));
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
