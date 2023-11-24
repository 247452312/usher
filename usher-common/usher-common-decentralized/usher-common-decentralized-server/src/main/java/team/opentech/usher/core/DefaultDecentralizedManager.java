package team.opentech.usher.core;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Objects;
import javax.annotation.Resource;
import team.opentech.usher.common.content.UsherDecentralizedContent;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.common.context.UsherDecentralizedContext.DecentralizedNode;
import team.opentech.usher.common.netty.DecentralizedConsumer;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.core.other.pojo.dto.AckDTO;
import team.opentech.usher.enums.RoleEnum;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.LogUtil;
import team.opentech.usher.util.RunnableUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月26日 08时40分
 */
public class DefaultDecentralizedManager implements DecentralizedManager {

    @Resource
    private IdUtil idUtil;

    @Override
    public void otherRequest(DecentralizedProtocol protocol) {

    }

    @Override
    public void dealAck(DecentralizedProtocol msg) throws UnsupportedEncodingException {
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
        /*0.判断机器还没有组成集群*/
        UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
        RoleEnum role = instance.role();
        if (role != RoleEnum.NO_ELECTIONS) {
            return;
        }
        /*1.判断有三台机器上线并且10秒内没有新节点加入*/
        String ip = null;
        Integer port = null;
        try {
            String body = msg.body(String.class);
            ip = body.substring(0, body.lastIndexOf(":"));
            port = Integer.valueOf(body.substring(body.lastIndexOf(":") + 1));
        } catch (UnsupportedEncodingException e) {
            Asserts.throwException(e);
        }
        List<DecentralizedNode> nodes = instance.waitNode();
        DecentralizedNode e = new DecentralizedNode(ip, port);
        if (nodes.contains(e)) {
            // 之前已存在的机器
            return;
        }
        nodes.add(e);
        long now = instance.flushLastWaitNodeTime();

        if (nodes.size() <= 2) {
            return;
        }

        RunnableUtil.runDelay(() -> {
            long lastWaitNodeTime = instance.lastWaitNodeTime();
            if (!Objects.equals(lastWaitNodeTime, now)) {
                // 说明10秒有机器上来了
                return;
            }
            /*2.发出组建集群提议*/
            DecentralizedConsumer decentralizedConsumer = instance.udpConsumer();
            String selfHost = instance.host();
            Integer selfPort = instance.serverPort();
            String onlineMsg = String.format("%s:%d", selfHost, selfPort);
            try {
                decentralizedConsumer.send(DecentralizedProtocol.build(instance.clusterTypeCode().getBytes(UsherDecentralizedContent.DEFAULT_CHARSET), idUtil.newId(), DecentralizedRequestTypeEnum.PROPOSE_FORMING_CLUSTER, onlineMsg.getBytes(UsherDecentralizedContent.DEFAULT_CHARSET)));
            } catch (InterruptedException ex) {
                LogUtil.error(this, ex);
            }
        }, 10);


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
