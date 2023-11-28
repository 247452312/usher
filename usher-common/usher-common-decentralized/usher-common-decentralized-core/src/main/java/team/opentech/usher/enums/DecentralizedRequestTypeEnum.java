package team.opentech.usher.enums;

import java.util.Objects;


/**
 * 去中心化请求类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月11日 09时40分
 */
public enum DecentralizedRequestTypeEnum {
    /**
     * 用于通用指定节点应答,非广域网通知
     */
    ACK(1 << 4, "ack", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 每个节点新上线时默认发出此类型请求
     */
    ONLINE_BROADCAST(2 << 4, "上线广播/定时广播", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 提议组建集群,udp类型通知
     */
    PROPOSE_FORMING_CLUSTER(3 << 4, "组建集群提议", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 和{@link DecentralizedRequestTypeEnum#PROPOSE_FORMING_CLUSTER}不同,上面是提议组建,接收回馈,这一条是确定了要组建集群.
     * 广域网通知类型,告知本地需要组建集群
     */
    FORMING_CLUSTER(4 << 4, "leader选举", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 每个节点对发起节点通知自己心目中的leader人选
     */
    PROPOSE_LEADER(5 << 4, "leader选票发送", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 发起节点对广域网公布leader人选,公式多次
     */
    LEADER_COMMISSION(6 << 4, "leader人选确认", DecentralizedNodeRoleEnum.NO_ELECTIONS),
    /**
     * 模主晋升通知,leader发向指定节点
     */
    MODULE_LEADER__COMMISSION(7 << 4, "模主晋升通知", DecentralizedNodeRoleEnum.LEADER),
    /**
     * 模块成员启用,通知成员模主节点以及模块内其他节点
     */
    ENABLE_MODEL_MEMBER(8 << 4, "模块成员启用", DecentralizedNodeRoleEnum.LEADER),
    /**
     * 模主通知模块成员联系容灾节点(信息中包含容灾节点与自身需要容灾的前节点)
     */
    CHANGE_DISASTER_RECOVERY_NODE(9 << 4, "容灾节点变更通知", DecentralizedNodeRoleEnum.MODEL_LEADER),
    /**
     * 模主通知给leader
     */
    MODEL_INIT_SUCCESS(10 << 4, "模块建立成功通知", DecentralizedNodeRoleEnum.MODEL_LEADER),
    /**
     * 节点根据模主的指示联系自己的容灾节点进行容灾初始化
     */
    DISASTER_RECOVERY_INIT(11 << 4, "容灾初始化请求", DecentralizedNodeRoleEnum.OTHER_NODE),
    /**
     * 容灾初始化完成后进行容灾数据正常传输
     */
    DISASTER_RECOVERY_DATA_TRANS(12 << 4, "容灾数据传输", DecentralizedNodeRoleEnum.OTHER_NODE),
    /**
     * 集群建立后的新节点才能收到此命令,leader通知节点属于某个模主
     */
    COMMISSION_MODEL_LEADER(13 << 4, "leader直接分配模主", DecentralizedNodeRoleEnum.LEADER),
    /**
     * 新节点上线后根据leader的任务去模主报道
     */
    NEW_NODE_CHECK_IN(14 << 4, "新节点报道", DecentralizedNodeRoleEnum.OTHER_NODE),
    /**
     * 发送心跳的节点识别到目标节点不存在,向模主发出节点下线通知,如果目标节点就是模主节点,则直接发送到leader
     */
    NODE_OFFLINE(15 << 4, "成员下线通知", DecentralizedNodeRoleEnum.OTHER_NODE),


    /*以下是逻辑控制请求*/

    /**
     * 正在选举的节点接收到另一个提议选举的请求,回应提议节点目前正在选举
     */
    IN_PROPOSE_LEADER(128 << 4, "选举正在进行通知", DecentralizedNodeRoleEnum.NO_ELECTIONS),

    /**
     * 已经进入工作的节点接收到选举提议,回应选举已经结束
     */
    PROPOSE_LEADER_END(129 << 4, "选举已经结束通知", DecentralizedNodeRoleEnum.OTHER_NODE),

    ;

    private final Integer code;

    private final String name;

    /**
     * 发送方的角色
     */
    private final DecentralizedNodeRoleEnum role;

    DecentralizedRequestTypeEnum(Integer code, String name, DecentralizedNodeRoleEnum role) {
        this.code = code;
        this.name = name;
        this.role = role;
    }

    public static DecentralizedRequestTypeEnum getByBlackCode(Integer code) {
        for (DecentralizedRequestTypeEnum value : values()) {
            if (Objects.equals(value.getCode(), code & ~0b1111)) {
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

    public DecentralizedNodeRoleEnum getRole() {
        return role;
    }
}
