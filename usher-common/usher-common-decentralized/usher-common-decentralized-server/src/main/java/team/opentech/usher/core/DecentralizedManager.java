package team.opentech.usher.core;

import java.io.UnsupportedEncodingException;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月25日 09时11分
 */
public interface DecentralizedManager {

    /**
     * 其他节点功能
     */
    void otherRequest(DecentralizedProtocol protocol);


    /**
     * 处理回应请求
     */
    void dealAck(DecentralizedProtocol msg) throws UnsupportedEncodingException;

    /**
     * 发送心跳的节点识别到目标节点不存在,向模主发出节点下线通知,如果目标节点就是模主节点,则直接发送到leader
     */
    void dealOffLine(DecentralizedProtocol msg);

    /**
     * 每个节点对发起节点通知自己心目中的leader人选
     */
    void dealProposeLeader(DecentralizedProtocol msg);

    /**
     * 和{@link DecentralizedRequestTypeEnum#PROPOSE_FORMING_CLUSTER}不同,上面是提议组建,接收回馈,这一条是确定了要组建集群.
     * 广域网通知类型,告知本地需要组建集群
     */
    void dealFormingCluster(DecentralizedProtocol msg);

    /**
     * 每个节点新上线时默认发出此类型请求
     */
    void dealOnlineBroadcast(DecentralizedProtocol msg);

    /**
     * 发起节点对广域网公布leader人选,公式多次
     */
    void dealLeaderCommission(DecentralizedProtocol msg);

    /**
     * 新节点上线后根据leader的任务去模主报道
     */
    void dealNewNodeCheckIn(DecentralizedProtocol msg);

    /**
     * 模主通知给leader
     */
    void dealModelInitSuccess(DecentralizedProtocol msg);

    /**
     * 模块成员启用,通知成员模主节点以及模块内其他节点
     */
    void dealEnableModelMember(DecentralizedProtocol msg);

    /**
     * 节点根据模主的指示联系自己的容灾节点进行容灾初始化
     */
    void dealDisasterRecoveryInit(DecentralizedProtocol msg);

    /**
     * 集群建立后的新节点才能收到此命令,leader通知节点属于某个模主
     */
    void dealCommissionModelLeader(DecentralizedProtocol msg);

    /**
     * 提议组建集群,udp类型通知
     */
    void dealProposeFormingCluster(DecentralizedProtocol msg);

    /**
     * 模主晋升通知,leader发向指定节点
     */
    void dealModelLeaderCommission(DecentralizedProtocol msg);

    /**
     * 容灾初始化完成后进行容灾数据正常传输
     */
    void dealDisasterRecoveryDataTrans(DecentralizedProtocol msg);

    /**
     * 模主通知模块成员联系容灾节点(信息中包含容灾节点与自身需要容灾的前节点)
     */
    void dealChangeDisasterRecoveryNode(DecentralizedProtocol msg);
}
