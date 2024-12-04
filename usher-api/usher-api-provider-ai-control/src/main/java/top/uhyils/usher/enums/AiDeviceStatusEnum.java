package top.uhyils.usher.enums;

import java.util.Objects;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月04日 19时32分
 */
public enum AiDeviceStatusEnum {
    /**
     * 同name
     */
    RECEPTOR(1, "传感器"),
    CONTROL(2, "控制器"),
    ;

    private final Integer code;

    private final String name;


    AiDeviceStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AiDeviceStatusEnum getByCode(Integer type) {
        for (AiDeviceStatusEnum value : values()) {
            if (Objects.equals(value.getCode(), type)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
