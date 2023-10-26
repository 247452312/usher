package team.opentech.usher.enums;

import java.util.Objects;


/**
 * 集群中节点角色
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月10日 09时32分
 */
public enum RoleEnum {
    /**
     * 管理全部节点,主要和模主通讯
     */
    LEADER(30, "leader"),
    /**
     * 管理模块下节点
     */
    MODEL_LEADER(20, "模主"),
    /**
     * 普通节点,和模主联系
     */
    OTHER_NODE(10, "其他节点"),
    /**
     * 还没进行选举
     */
    NO_ELECTIONS(-10, "未选举状态"),
    ;

    private final Integer code;

    private final String name;

    RoleEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RoleEnum getByCode(Integer code) {
        for (RoleEnum value : values()) {
            if (Objects.equals(value.getCode(), code)) {
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
