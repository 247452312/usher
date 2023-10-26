package team.opentech.usher.core;

import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.core.other.pojo.dto.AckDTO;
import team.opentech.usher.core.other.pojo.dto.OnlineBroadcastDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月26日 08时40分
 */
public class DefaultDecentralizedManager implements DecentralizedManager {

    @Override
    public void otherRequest(DecentralizedProtocol protocol) {

    }

    @Override
    public void dealAck(DecentralizedProtocol msg) {
        AckDTO body = msg.body(AckDTO.class);

    }

    @Override
    public void dealOffLine(DecentralizedProtocol msg) {

    }

    @Override
    public void dealProposeLeader(DecentralizedProtocol msg) {

    }

    @Override
    public void dealFormingCluster(DecentralizedProtocol msg) {

    }

    @Override
    public void dealOnlineBroadcast(DecentralizedProtocol msg) {
        OnlineBroadcastDTO body = msg.body(OnlineBroadcastDTO.class);
        String ip = body.getIp();
        NodeInfo instance = NodeInfo.getInstance();
        // 1.判断

    }

    @Override
    public void dealLeaderCommission(DecentralizedProtocol msg) {

    }

    @Override
    public void dealNewNodeCheckIn(DecentralizedProtocol msg) {

    }

    @Override
    public void dealModelInitSuccess(DecentralizedProtocol msg) {

    }

    @Override
    public void dealEnableModelMember(DecentralizedProtocol msg) {

    }

    @Override
    public void dealDisasterRecoveryInit(DecentralizedProtocol msg) {

    }

    @Override
    public void dealCommissionModelLeader(DecentralizedProtocol msg) {

    }

    @Override
    public void dealProposeFormingCluster(DecentralizedProtocol msg) {

    }

    @Override
    public void dealModelLeaderCommission(DecentralizedProtocol msg) {

    }

    @Override
    public void dealDisasterRecoveryDataTrans(DecentralizedProtocol msg) {

    }

    @Override
    public void dealChangeDisasterRecoveryNode(DecentralizedProtocol msg) {

    }
}
