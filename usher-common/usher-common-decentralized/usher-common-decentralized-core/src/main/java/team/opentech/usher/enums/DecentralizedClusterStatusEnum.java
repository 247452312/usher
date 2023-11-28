package team.opentech.usher.enums;

import java.util.Objects;


/**
 * 集群状态
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月10日 09时29分
 */
public enum DecentralizedClusterStatusEnum {
    /**
     * 刚上线默认状态,这时通过网络UDP扫描进行与其他节点的交互
     */
    INIT(0, "初始状态"),
    /**
     * 互认节点到达三个或以上且10秒内没有发现新节点,则互认的节点进入选举状态
     */
    IN_ELECTIONS(10, "选举状态"),
    /**
     * 正常工作状态,这时节点有可能是leader,模主,其他节点
     */
    WORKING(20, "工作状态"),
    /**
     * 节点此时如果10秒内除了自己没有发现任何节点,则进入离线单机状态,可以向外发布功能,但是不再进行udp扫描
     */
    OFFLINE(-10, "离线单机状态"),
    ;

    private final Integer code;

    private final String name;

    DecentralizedClusterStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DecentralizedClusterStatusEnum getByCode(Integer code) {
        for (DecentralizedClusterStatusEnum value : values()) {
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
