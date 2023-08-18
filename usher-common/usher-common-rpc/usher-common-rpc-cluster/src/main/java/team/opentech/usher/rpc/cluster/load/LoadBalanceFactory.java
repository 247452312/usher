package team.opentech.usher.rpc.cluster.load;

import team.opentech.usher.rpc.cluster.enums.LoadBalanceEnum;
import team.opentech.usher.rpc.cluster.pojo.NettyInfo;
import team.opentech.usher.rpc.netty.RpcNetty;
import team.opentech.usher.rpc.spi.RpcSpiManager;
import java.util.Map;

/**
 * 负载均衡器工厂
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 09时52分
 */
public class LoadBalanceFactory {

    private LoadBalanceFactory() {
    }

    /**
     * 根据负载均衡枚举获取loadBalance
     *
     * @param loadBalanceEnum 负载均衡枚举
     * @param nettyMap        nettymap
     *
     * @return
     */
    public static LoadBalanceInterface createByLoadBalanceEnum(LoadBalanceEnum loadBalanceEnum, Map<NettyInfo, RpcNetty> nettyMap) throws InterruptedException {
        // 返回一个构造完成的消费者
        return (LoadBalanceInterface) RpcSpiManager.createOrGetExtensionByClass(LoadBalanceInterface.class, loadBalanceEnum.getSpiName(), nettyMap);

    }

}
