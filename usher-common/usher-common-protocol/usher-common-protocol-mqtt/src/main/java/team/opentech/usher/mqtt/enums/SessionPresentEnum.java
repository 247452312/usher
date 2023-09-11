package team.opentech.usher.mqtt.enums;

import java.util.Arrays;
import java.util.Objects;
import team.opentech.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月11日 14时18分
 */
public enum SessionPresentEnum {
    /**
     * 意义同name
     */
    ACCEPTED((byte) 0, "已接受连接"),
    // todo 还有很多 mqtt文档 http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html#_Figure_3.8_%E2%80%93
    ;


    private final byte code;

    private final String name;

    SessionPresentEnum(byte code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SessionPresentEnum getByCode(byte code) {
        return Arrays.stream(values()).filter(t -> Objects.equals(t.code, code)).findFirst().orElseThrow(() -> Asserts.makeException("未找到session类型:{}", code));
    }

    public byte getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
