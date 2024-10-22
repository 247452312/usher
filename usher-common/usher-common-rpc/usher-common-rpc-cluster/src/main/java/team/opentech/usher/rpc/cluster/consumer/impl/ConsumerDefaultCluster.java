package team.opentech.usher.rpc.cluster.consumer.impl;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.cluster.Cluster;
import team.opentech.usher.rpc.cluster.enums.LoadBalanceEnum;
import team.opentech.usher.rpc.cluster.load.LoadBalanceFactory;
import team.opentech.usher.rpc.cluster.load.LoadBalanceInterface;
import team.opentech.usher.rpc.cluster.pojo.NettyInfo;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;
import team.opentech.usher.rpc.exception.RpcException;
import team.opentech.usher.rpc.exception.RpcNetException;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.RpcNetty;
import team.opentech.usher.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import team.opentech.usher.rpc.netty.enums.RpcNettyTypeEnum;
import team.opentech.usher.rpc.netty.factory.RpcNettyFactory;
import team.opentech.usher.rpc.netty.pojo.NettyInitDto;
import team.opentech.usher.util.LogUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消费者默认的cluster
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 12时23分
 */
@RpcSpi(single = false)
public class ConsumerDefaultCluster implements Cluster {

    /**
     * 需要负载均衡的netty们
     */
    private Map<NettyInfo, RpcNetty> nettyMap;

    /**
     * 负载均衡策略
     */
    private LoadBalanceEnum loadBalanceType;

    /**
     * 接口名称
     */
    private Class<?> target;


    /**
     * @initParam 1 Clazz consumer要封装的rpc类
     * @initParam 2 NettyInitDto[] netty初始化需要的东西
     * @initParam 3 LoadBalanceEnum(可选) netty初始化需要的东西
     */
    @Override
    public void init(Object... params) throws InterruptedException {

        // 入参由 0 - class  1 - nettyInitDto[] 2 - LoadBalanceEnum(可选) 负载均衡,不选默认随机算法
        this.target = (Class<?>) params[0];
        NettyInitDto[] nettyInits = (NettyInitDto[]) params[1];
        if (params.length > 2) {
            this.loadBalanceType = (LoadBalanceEnum) params[2];
        } else {
            this.loadBalanceType = LoadBalanceEnum.RANDOM;
        }

        boolean self = false;
        for (NettyInitDto nettyInit : nettyInits) {
            if (nettyInit.getSelfService()) {
                self = true;
                break;
            }
        }
        HashMap<NettyInfo, RpcNetty> initNettyMap;
        if (self) {
            initNettyMap = new HashMap<>(1);
            RpcNetty netty;
            try {
                netty = RpcNettyFactory.createSelfNetty(target);
            } catch (Exception e) {
                throw new RpcException(e);
            }
            NettyInfo nettyInfo = new NettyInfo(1, null, null, null);
            initNettyMap.put(nettyInfo, netty);
        } else {
            initNettyMap = new HashMap<>(nettyInits.length);
            for (int i = 0; i < nettyInits.length; i++) {
                NettyInitDto nettyInit = nettyInits[i];
                RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.CONSUMER, nettyInit);
                NettyInfo nettyInfo = new NettyInfo(i, nettyInit.getWeight(), nettyInit.getHost(), nettyInit.getPort());
                initNettyMap.put(nettyInfo, netty);
            }
        }
        this.nettyMap = initNettyMap;
    }

    @Override
    public String getInterfaceName() {
        return this.target.getName();
    }

    @Override
    public LoadBalanceEnum getTypeOfLoadBalance() {
        return loadBalanceType;
    }

    @Override
    public Boolean isSingle() {
        return nettyMap.size() == 1;
    }

    @Override
    public Integer getNumOfColony() {
        return nettyMap.size();
    }

    @Override
    public Map<NettyInfo, RpcNetty> getAllNetty() {
        return nettyMap;
    }

    @Override
    public Boolean shutdown() {
        boolean result = Boolean.TRUE;
        for (Map.Entry<NettyInfo, RpcNetty> nettyInfoRpcNettyEntry : nettyMap.entrySet()) {
            boolean shutdown = nettyInfoRpcNettyEntry.getValue().shutdown();
            if (!shutdown) {
                result = Boolean.FALSE;
            }
        }
        return result;
    }

    @Override
    public RpcData sendMsg(RpcData rpcData, SendInfo info) throws InterruptedException {
        if (nettyMap.isEmpty()) {
            String interfaceName = getInterfaceName();
            throw new RpcNetException("指定的服务端 " + interfaceName + " 不存在");
        }
        LoadBalanceInterface loadBalance = LoadBalanceFactory.createByLoadBalanceEnum(loadBalanceType, nettyMap);
        return loadBalance.send(rpcData, info, nettyMap);

    }

    @Override
    public Boolean onServiceStatusChange(List<NettyInfo> nettyInfos) {
        // 筛选出没有的,移出->下线
        Set<NettyInfo> set = new HashSet<>();
        for (NettyInfo nettyInfo : nettyMap.keySet()) {
            if (!nettyInfos.contains(nettyInfo)) {
                set.add(nettyInfo);
            }
        }
        for (NettyInfo nettyInfo : set) {
            RpcNetty rpcNetty = nettyMap.get(nettyInfo);
            rpcNetty.shutdown();
            nettyMap.remove(nettyInfo);
        }
        // 筛选不存在的,添加->上线
        for (NettyInfo nettyInfo : nettyInfos) {
            if (nettyMap.containsKey(nettyInfo)) {
                continue;
            }
            NettyInitDto nettyInit = new NettyInitDto();
            nettyInit.setHost(nettyInfo.getHost());
            nettyInit.setPort(nettyInfo.getPort());
            nettyInit.setCallback(new RpcDefaultResponseCallBack());
            try {
                RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.CONSUMER, nettyInit);
                nettyMap.put(nettyInfo, netty);
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
        }
        return Boolean.TRUE;
    }
}
