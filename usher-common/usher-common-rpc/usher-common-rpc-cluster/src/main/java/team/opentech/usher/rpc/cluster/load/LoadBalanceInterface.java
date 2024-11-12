package team.opentech.usher.rpc.cluster.load;

import java.util.Map;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.core.RpcNettyConsumer;
import team.opentech.usher.rpc.netty.pojo.NettyInitDto;
import team.opentech.usher.rpc.spi.RpcSpiExtension;

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
     * @param rpcSendData   要发送的信息
     * @param info          发送时相关的信息
     * @param interfaceName 接口名称
     * @param nettyMap      负载均衡的netty们
     *
     * @return 返回的信息
     */
    RpcData send(RpcData rpcSendData, SendInfo info, String interfaceName, Map<NettyInitDto, RpcNettyConsumer> nettyMap) throws InterruptedException;
}
