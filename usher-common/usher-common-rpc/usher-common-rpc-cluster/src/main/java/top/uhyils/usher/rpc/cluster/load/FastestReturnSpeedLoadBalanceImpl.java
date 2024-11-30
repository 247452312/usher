package top.uhyils.usher.rpc.cluster.load;

import java.util.HashMap;
import java.util.Map;
import top.uhyils.usher.UsherThreadLocal;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.core.RpcNettyConsumer;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

/**
 * 最快返回速度
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 09时14分
 */
@RpcSpi
public class FastestReturnSpeedLoadBalanceImpl extends AbstractLoadBalance {

    /**
     * 记录返回时间用
     */
    private UsherThreadLocal<Long> timeThreadLocal;

    private Map<NettyInitDto, Long> lastFiveSendAvgTimeMap = new HashMap<>();

    @Override
    protected NettyInitDto getNettyInfo(SendInfo info, Map<NettyInitDto, RpcNettyConsumer> nettyMap) {
        NettyInitDto fastNettyInfo = null;
        long minTime = 0;
        for (NettyInitDto nettyInfo : nettyMap.keySet()) {
            Long lastFiveSendAvgTime = lastFiveSendAvgTimeMap.putIfAbsent(fastNettyInfo, null);
            // 如果一个netty一次都没有执行过,那么就选它
            if (lastFiveSendAvgTime == null) {
                fastNettyInfo = nettyInfo;
                break;
            }
            if (lastFiveSendAvgTime < minTime) {
                minTime = lastFiveSendAvgTime;
                fastNettyInfo = nettyInfo;
            }
        }
        return fastNettyInfo;
    }

    @Override
    protected int getType() {
        return NETTY_INFO_TYPE;
    }

    @Override
    protected void preprocessing(NettyInitDto nettyInfo, RpcNettyConsumer netty) {
        long l = System.currentTimeMillis();
        timeThreadLocal = new UsherThreadLocal<>();
        timeThreadLocal.set(l);
    }

    @Override
    protected void postProcessing(NettyInitDto nettyInfo, RpcNettyConsumer netty, RpcData rpcData) {
        long runTime = System.currentTimeMillis() - timeThreadLocal.get();
        timeThreadLocal.remove();
        Long lastFiveSendAvgTime = lastFiveSendAvgTimeMap.putIfAbsent(nettyInfo, runTime);
        if (lastFiveSendAvgTime == null) {
            lastFiveSendAvgTime = runTime;
        }
        long lastTime = (lastFiveSendAvgTime * 5 + runTime) / 6L;
        lastFiveSendAvgTimeMap.put(nettyInfo, lastTime);
    }

    @Override
    protected void exceptionHandle(NettyInitDto nettyInfo, RpcNettyConsumer rpcNetty, Exception e) {
        timeThreadLocal.remove();
    }
}
