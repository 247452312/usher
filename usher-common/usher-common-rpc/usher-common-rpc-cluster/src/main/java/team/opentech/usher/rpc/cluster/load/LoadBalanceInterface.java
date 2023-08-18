package team.opentech.usher.rpc.cluster.load;

import team.opentech.usher.rpc.cluster.pojo.NettyInfo;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.RpcNetty;
import team.opentech.usher.rpc.spi.RpcSpiExtension;
import java.util.Map;

/**
 * 负载均衡
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 08时38分
 */
public interface LoadBalanceInterface extends RpcSpiExtension {

    /**
     * 发送信息
     *
     * @param rpcSendData 要发送的信息
     * @param info        发送时相关的信息
     * @param nettyMap    负载均衡的netty们
     *
     * @return 返回的信息
     */
    RpcData send(RpcData rpcSendData, SendInfo info, Map<NettyInfo, RpcNetty> nettyMap) throws InterruptedException;
}
