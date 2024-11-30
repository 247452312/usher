package top.uhyils.usher.rpc.cluster.consumer.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.cluster.Cluster;
import top.uhyils.usher.rpc.cluster.enums.LoadBalanceEnum;
import top.uhyils.usher.rpc.cluster.load.LoadBalanceFactory;
import top.uhyils.usher.rpc.cluster.load.LoadBalanceInterface;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;
import top.uhyils.usher.rpc.exception.RpcException;
import top.uhyils.usher.rpc.exception.RpcNetException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.RpcNetty;
import top.uhyils.usher.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import top.uhyils.usher.rpc.netty.core.RpcNettyConsumer;
import top.uhyils.usher.rpc.netty.factory.RpcNettyFactory;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.util.LogUtil;

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
    protected volatile Map<NettyInitDto, RpcNettyConsumer> nettyMap = new ConcurrentHashMap<>();

    /**
     * 离线的netty
     */
    protected volatile Map<NettyInitDto, RpcNettyConsumer> offLineNettyMap = new ConcurrentHashMap<>();

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

        buildNetty(nettyInits);
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
    public Map<NettyInitDto, RpcNetty> getAllNetty() {
        Map<NettyInitDto, RpcNetty> nettyInfoRpcNettyHashMap = new HashMap<>();
        nettyInfoRpcNettyHashMap.putAll(nettyMap);
        return nettyInfoRpcNettyHashMap;
    }

    @Override
    public Boolean shutdown() {
        boolean result = Boolean.TRUE;
        for (Map.Entry<NettyInitDto, RpcNettyConsumer> nettyInfoRpcNettyEntry : nettyMap.entrySet()) {
            boolean shutdown = nettyInfoRpcNettyEntry.getValue().shutdown();
            if (!shutdown) {
                result = Boolean.FALSE;
            }
        }
        return result;
    }

    @Override
    public void onOffLine(NettyInitDto nettyInitDto) {
        if (this.nettyMap.containsKey(nettyInitDto)) {
            synchronized (ReConnCallBackImpl.class) {
                if (this.nettyMap.containsKey(nettyInitDto)) {
                    RpcNettyConsumer remove = this.nettyMap.remove(nettyInitDto);
                    this.offLineNettyMap.put(nettyInitDto, remove);
                }
            }
        }
    }

    @Override
    public void onReConn(NettyInitDto nettyInitDto) {
        if (this.offLineNettyMap.containsKey(nettyInitDto)) {
            synchronized (ReConnCallBackImpl.class) {
                if (this.offLineNettyMap.containsKey(nettyInitDto)) {
                    RpcNettyConsumer remove = this.offLineNettyMap.remove(nettyInitDto);
                    this.nettyMap.put(nettyInitDto, remove);
                }
            }
        }
    }

    @Override
    public RpcData sendMsg(RpcData rpcData, SendInfo info) throws InterruptedException {
        if (nettyMap.isEmpty()) {
            String interfaceName = getInterfaceName();
            throw new RpcNetException("指定的服务端 " + interfaceName + " 不存在");
        }
        LoadBalanceInterface loadBalance = LoadBalanceFactory.createByLoadBalanceEnum(loadBalanceType, nettyMap);
        return loadBalance.send(rpcData, info, getInterfaceName(), nettyMap);

    }

    @Override
    public Boolean onServiceStatusChange(List<NettyInitDto> nettyInfos) {
        // 筛选出没有的,移出->下线
        Set<NettyInitDto> set = new HashSet<>();
        for (NettyInitDto nettyInfo : nettyMap.keySet()) {
            if (!nettyInfos.contains(nettyInfo)) {
                set.add(nettyInfo);
            }
        }
        for (NettyInitDto nettyInfo : set) {
            RpcNetty rpcNetty = nettyMap.get(nettyInfo);
            rpcNetty.shutdown();
            nettyMap.remove(nettyInfo);
        }
        // 筛选不存在的,添加->上线
        for (NettyInitDto nettyInfo : nettyInfos) {
            if (nettyMap.containsKey(nettyInfo)) {
                continue;
            }
            NettyInitDto nettyInit = new NettyInitDto();
            nettyInit.setHost(nettyInfo.getHost());
            nettyInit.setPort(nettyInfo.getPort());
            nettyInit.setCallback(new RpcDefaultResponseCallBack());
            try {
                RpcNetty netty = RpcNettyFactory.createConsumer(nettyInit, 1000L * 60 * 60, new ReConnCallBackImpl(this));
                nettyMap.put(nettyInfo, (RpcNettyConsumer) netty);
            } catch (Exception e) {
                LogUtil.error(this, e);
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 构建netty层
     *
     * @param nettyInits
     *
     * @throws InterruptedException
     */
    private void buildNetty(NettyInitDto[] nettyInits) throws InterruptedException {
        boolean self = false;
        for (NettyInitDto nettyInit : nettyInits) {
            if (nettyInit.getSelfService()) {
                self = true;
                break;
            }
        }
        // 如果调用的是自身的服务,那么就完全不会调用集群中其他的自己,而是全部调用到自身的服务中去
        if (self) {
            RpcNettyConsumer netty;
            try {
                netty = RpcNettyFactory.createSelfNetty(target);
            } catch (Exception e) {
                throw new RpcException(e);
            }
            NettyInitDto nettyInfo = NettyInitDto.build(1, null, null, null, null);
            nettyMap.put(nettyInfo, netty);
        } else {
            for (int i = 0; i < nettyInits.length; i++) {
                NettyInitDto nettyInit = nettyInits[i];
                RpcNetty netty = RpcNettyFactory.createConsumer(nettyInit, 1000L * 60 * 60, new ReConnCallBackImpl(this));
                nettyInit.setIndexInColony(i);
                nettyMap.put(nettyInit, (RpcNettyConsumer) netty);
            }
        }
    }
}
