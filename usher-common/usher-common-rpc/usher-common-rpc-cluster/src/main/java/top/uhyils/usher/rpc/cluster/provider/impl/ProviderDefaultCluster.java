package top.uhyils.usher.rpc.cluster.provider.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.cluster.enums.LoadBalanceEnum;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;
import top.uhyils.usher.rpc.cluster.provider.AbstractProviderCluster;
import top.uhyils.usher.rpc.exception.RpcSpiInitException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.factory.RpcBeanFactory;
import top.uhyils.usher.rpc.netty.RpcNetty;
import top.uhyils.usher.rpc.netty.callback.RpcCallBackFactory;
import top.uhyils.usher.rpc.netty.factory.RpcNettyFactory;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.util.IpUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 15时36分
 */
@RpcSpi
public class ProviderDefaultCluster extends AbstractProviderCluster {

    /**
     * netty信息
     */
    private NettyInitDto nettyInfo;

    /**
     * netty的本体
     */
    private RpcNetty netty;

    /**
     * 负载均衡策略
     */
    private LoadBalanceEnum loadBalanceType;


    @Override
    public void init(Object... params) throws InterruptedException {

        Integer port = (Integer) params[0];
        Map<String, Object> beans = (Map<String, Object>) params[1];

        NettyInitDto nettyInit = new NettyInitDto();

        try {
            nettyInit.setCallback(RpcCallBackFactory.createRequestCallBack(RpcBeanFactory.getInstance(beans).getRpcBeans()));
        } catch (Exception e) {
            throw new RpcSpiInitException(e, this.getClass());
        }
        nettyInit.setHost(IpUtil.getIp());
        nettyInit.setPort(port);
        RpcNetty initNetty = RpcNettyFactory.createProvider(nettyInit, 1000L * 60 * 60);
        NettyInitDto initNettyInfo = new NettyInitDto();
        initNettyInfo.setIndexInColony(1);

        this.nettyInfo = initNettyInfo;
        this.netty = initNetty;
        this.loadBalanceType = LoadBalanceEnum.RANDOM;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public LoadBalanceEnum getTypeOfLoadBalance() {
        return loadBalanceType;
    }

    @Override
    public Boolean isSingle() {
        return Boolean.TRUE;
    }

    @Override
    public Integer getNumOfColony() {
        return 1;
    }

    @Override
    public Map<NettyInitDto, RpcNetty> getAllNetty() {
        Map<NettyInitDto, RpcNetty> nettyInfoRpcNettyMap = new HashMap<>(1);
        nettyInfoRpcNettyMap.put(nettyInfo, netty);
        return nettyInfoRpcNettyMap;
    }

    @Override
    public Boolean shutdown() {
        return netty.shutdown();
    }

    @Override
    public RpcData sendMsg(RpcData rpcData, SendInfo info) throws InterruptedException {
        return netty.sendMsg(rpcData);
    }


    @Override
    public Boolean onServiceStatusChange(List<NettyInitDto> nettyInfos) {
        return Boolean.TRUE;
    }

    @Override
    public Integer getIndexInColony() {
        return 1;
    }

    @Override
    public Float getWeight() {
        return 0F;
    }

    @Override
    public void onOffLine(NettyInitDto nettyInitDto) {

    }

    @Override
    public void onReConn(NettyInitDto nettyInitDto) {

    }
}
