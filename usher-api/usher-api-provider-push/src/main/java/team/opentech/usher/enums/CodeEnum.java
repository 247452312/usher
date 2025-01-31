package team.opentech.usher.enums;

/**
 * 字符编码
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月28日 09时28分
 */
public enum CodeEnum {
    /**
     * UTF-8
     */
    UTF_8("UTF-8"),

    /**
     * unicode
     */
    UNICODE("unicode");

    private String code;

    CodeEnum(String code) {
        this.code = code;
    }

    public static CodeEnum prase(String code) {
        if (UTF_8.code.equalsIgnoreCase(code)) {
            return UTF_8;
        } else if (UNICODE.code.equalsIgnoreCase(code)) {
            return UNICODE;
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
