package top.uhyils.usher.enums;

import java.util.Objects;
import java.util.function.Function;
import top.uhyils.usher.annotation.NotNull;

/**
 * 用户令牌时效
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 16时18分
 */
public enum UserTokenValidityEnum {
    ONE_DAY(1, "一天", time -> time + 24 * 60 * 60 * 1000L),
    ONE_WEEK(2, "一周", time -> time + 7 * 24 * 60 * 60 * 1000L),
    ONE_MONTH(3, "一月", time -> time + 30 * 24 * 60 * 60 * 1000L),
    ONE_YEAR(4, "一年", time -> time + 365 * 24 * 60 * 60 * 1000L),
    FOREVER(5, "永久", time -> Long.MAX_VALUE);

    private final Integer code;

    private final String name;

    private final Function<Long, Long> dataRunResult;


    UserTokenValidityEnum(Integer code, String name, Function<Long, Long> dataRunResult) {
        this.code = code;
        this.name = name;
        this.dataRunResult = dataRunResult;
    }

    public static UserTokenValidityEnum getByCode(Integer code) {
        for (UserTokenValidityEnum value : values()) {
            if (Objects.equals(value.code, code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 生成过期时间
     *
     * @return
     */
    @NotNull
    public Long makeExpirationDate() {
        return dataRunResult.apply(System.currentTimeMillis());
    }


    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
