package top.uhyils.usher.enums;

import java.util.Objects;

/**
 * 设备链接类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 14时10分
 */
public enum AiDeviceLinkTypeEnum {

    /**
     * 同name
     */
    HTTP(1, "HTTP链接"),
    ;

    private final Integer code;

    private final String name;


    AiDeviceLinkTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static AiDeviceLinkTypeEnum getByCode(Integer type) {
        for (AiDeviceLinkTypeEnum value : values()) {
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
