package top.uhyils.usher.mq.core;

/**
 * rocketMq收到消息的回应
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月09日 13时24分
 */
public enum RocketMqMessageResEnum {
    SUCCESS(0, "成功"),
    ERROR(1, "失败"),
    ;

    private final Integer code;

    private final String name;


    RocketMqMessageResEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
