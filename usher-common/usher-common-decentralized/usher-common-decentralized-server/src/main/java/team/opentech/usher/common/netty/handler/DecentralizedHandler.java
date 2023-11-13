package team.opentech.usher.common.netty.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.util.Asserts;

/**
 * 去中心化集群handler
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月11日 09时18分
 */
public class DecentralizedHandler extends SimpleChannelInboundHandler<DecentralizedProtocol> {

    private final DecentralizedManager manager;

    public DecentralizedHandler(DecentralizedManager manager) {
        this.manager = manager;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }

    /**
     * 初始化连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        if (channel.isActive()) {
            ctx.close();
        }
    }

    /**
     * 接收到信息时调用
     *
     * @param ctx
     * @param msg
     *
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DecentralizedProtocol msg) throws Exception {
        UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
        Redisable redis = instance.cache();
        super.channelRead(ctx, msg);
        String cacheKey = msg.cacheKey();
        Boolean exists = redis.exists(cacheKey);
        if (exists) {
            return;
        }
        redis.set(cacheKey, null);
        redis.expire(cacheKey, 20);

        int typeCode = msg.requestType();
        DecentralizedRequestTypeEnum byBlackCode = DecentralizedRequestTypeEnum.getByBlackCode(typeCode);
        if (byBlackCode == null) {
            //自定义请求
            manager.otherRequest(msg);
        } else {
            switch (byBlackCode) {
                case ACK:
                    manager.dealAck(msg);
                    break;
                case NODE_OFFLINE:
                    manager.dealOffLine(msg);
                    break;
                case PROPOSE_LEADER:
                    manager.dealProposeLeader(msg);
                    break;
                case FORMING_CLUSTER:
                    manager.dealFormingCluster(msg);
                    break;
                case ONLINE_BROADCAST:
                    manager.dealOnlineBroadcast(msg);
                    break;
                case LEADER_COMMISSION:
                    manager.dealLeaderCommission(msg);
                    break;
                case NEW_NODE_CHECK_IN:
                    manager.dealNewNodeCheckIn(msg);
                    break;
                case MODEL_INIT_SUCCESS:
                    manager.dealModelInitSuccess(msg);
                    break;
                case ENABLE_MODEL_MEMBER:
                    manager.dealEnableModelMember(msg);
                    break;
                case DISASTER_RECOVERY_INIT:
                    manager.dealDisasterRecoveryInit(msg);
                    break;
                case COMMISSION_MODEL_LEADER:
                    manager.dealCommissionModelLeader(msg);
                    break;
                case PROPOSE_FORMING_CLUSTER:
                    manager.dealProposeFormingCluster(msg);
                    break;
                case MODULE_LEADER__COMMISSION:
                    manager.dealModelLeaderCommission(msg);
                    break;
                case DISASTER_RECOVERY_DATA_TRANS:
                    manager.dealDisasterRecoveryDataTrans(msg);
                    break;
                case CHANGE_DISASTER_RECOVERY_NODE:
                    manager.dealChangeDisasterRecoveryNode(msg);
                    break;
                default:
                    Asserts.throwException("未支持的去中心化集群请求类型");
            }
        }

    }
}
