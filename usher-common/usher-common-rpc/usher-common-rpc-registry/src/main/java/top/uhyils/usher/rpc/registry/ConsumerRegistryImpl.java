package top.uhyils.usher.rpc.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.cluster.Cluster;
import top.uhyils.usher.rpc.cluster.ClusterFactory;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.content.ClusterNameContext;
import top.uhyils.usher.rpc.exception.RpcNetException;
import top.uhyils.usher.rpc.exception.RpcShowDownException;
import top.uhyils.usher.rpc.exchange.pojo.content.impl.RpcRequestContentImpl;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.callback.RpcCallBackFactory;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.rpc.registry.mode.ConsumerRegistryCenterHandler;
import top.uhyils.usher.rpc.registry.mode.RegistryCenterHandler;
import top.uhyils.usher.rpc.registry.mode.RegistryCenterHandlerFactory;
import top.uhyils.usher.rpc.registry.pojo.RegistryModelInfo;
import top.uhyils.usher.rpc.registry.pojo.RegistryProviderNecessaryInfo;
import top.uhyils.usher.rpc.registry.pojo.event.RegistryEvent;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.CollectionUtil;
import top.uhyils.usher.util.IpUtil;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年04月23日 14时01分
 */
@RpcSpi(single = false)
public class ConsumerRegistryImpl extends AbstractRegistry implements ConsumerRegistry {

    /**
     * 发送者的ip
     */
    private final String selfIp;

    /**
     * 注册中心对应的集群信息
     * key->集群名称 value->集群
     */
    private Map<String, Cluster> clusters;

    public ConsumerRegistryImpl() {
        this.selfIp = IpUtil.getIp();
    }

    @Override
    public void init(Object... objects) throws InterruptedException {
        super.init(objects);
        initClusters();
        ConsumerRegistryCenterHandler registryCenterHandler = (ConsumerRegistryCenterHandler) this.registryCenterHandler;
        registryCenterHandler.registerEvent(this::onEvent);
    }

    @Override
    public RpcData invoke(RpcData rpcData) throws InterruptedException {
        String clusterName = ClusterNameContext.get();
        if (StringUtil.isNotEmpty(clusterName)) {
            Asserts.assertTrue(clusters.containsKey(clusterName), "执行时集群中不存在指定的集群名称:{}", clusterName);
            Cluster cluster = clusters.get(clusterName);
            return cluster.sendMsg(rpcData, new SendInfo(selfIp));
        }
        Optional<Entry<String, Cluster>> any = clusters.entrySet().stream().findAny();
        RpcRequestContentImpl content = (RpcRequestContentImpl) rpcData.content();
        Asserts.assertTrue(any.isPresent(), "指定接口未发现可用的服务:{},版本:{}", content.getServiceName(), content.getServiceVersion());
        Entry<String, Cluster> stringClusterEntry = any.get();
        Cluster value = stringClusterEntry.getValue();
        return value.sendMsg(rpcData, new SendInfo(selfIp));
    }

    @Override
    public Boolean close() {
        boolean down = true;
        for (Entry<String, Cluster> clusterEntry : clusters.entrySet()) {
            Cluster value = clusterEntry.getValue();
            Boolean shutdown = value.shutdown();
            if (!shutdown) {
                down = false;
            }
        }
        if (!down) {
            throw new RpcShowDownException("rpc关闭错误");
        }
        return true;
    }

    @Override
    protected RegistryCenterHandler doInitRegistryCenterHandler(Object... objects) throws InterruptedException {
        return RegistryCenterHandlerFactory.createConsumer(serviceClass);
    }

    private void initClusters() throws InterruptedException {

        /*构建netty初始化需要的信息*/
        List<RegistryModelInfo> registryModelInfos = this.registryCenterHandler.cacheInfo();
        if (CollectionUtil.isEmpty(registryModelInfos)) {
            throw new RpcNetException("不存在指定的服务:" + this.serviceClass.getName());
        }
        // key为集群名称 value为集群中多台服务器的信息
        Map<String, List<RegistryModelInfo>> nameClusterMap = registryModelInfos.stream().filter(t -> t.getNecessaryInfo() != null).collect(Collectors.groupingBy(t -> t.getNecessaryInfo().getClusterName()));

        Map<String, Cluster> results = new HashMap<>(nameClusterMap.size());
        for (Entry<String, List<RegistryModelInfo>> entry : nameClusterMap.entrySet()) {
            String clusterName = entry.getKey();
            List<RegistryModelInfo> registryModelInfoList = entry.getValue();

            // 通过集群中多态机器的信息来创建集群
            Cluster cluster = createCluster(registryModelInfoList);
            results.put(clusterName, cluster);
        }

        this.clusters = results;
    }

    private void onEvent(RegistryEvent event) {
        Map<String, List<NettyInitDto>> registryNettyInfoMap = event.getRegistryNettyInfoMap();
        for (Entry<String, List<NettyInitDto>> entry : registryNettyInfoMap.entrySet()) {
            String clusterName = entry.getKey();
            List<NettyInitDto> value = entry.getValue();
            for (NettyInitDto nettyInitDto : value) {
                nettyInitDto.setCallback(RpcCallBackFactory.createResponseCallBack());
            }
            if (clusters.containsKey(clusterName)) {
                Cluster cluster = clusters.get(clusterName);
                cluster.onServiceStatusChange(value);
            } else {
                try {
                    Cluster defaultConsumerCluster = ClusterFactory.createDefaultConsumerCluster(this.serviceClass, value.toArray(new NettyInitDto[0]));
                    clusters.put(clusterName, defaultConsumerCluster);
                } catch (Exception e) {
                    LogUtil.error(this, e);
                }

            }
        }
        for (String clusterName : clusters.keySet()) {
            if (!registryNettyInfoMap.containsKey(clusterName)) {
                Cluster cluster = clusters.get(clusterName);
                cluster.shutdown();
                clusters.remove(clusterName);
            }
        }
    }

    /**
     * 创建单个集群信息
     *
     * @param registryInfos
     *
     * @return
     */
    private Cluster createCluster(List<RegistryModelInfo> registryInfos) throws InterruptedException {
        // 获取目标接口的信息
        NettyInitDto[] nettyInits = new NettyInitDto[registryInfos.size()];
        for (int i = 0; i < registryInfos.size(); i++) {
            RegistryModelInfo registryInfo = registryInfos.get(i);
            //查询到目标class注册到注册中心的信息
            RegistryProviderNecessaryInfo necessaryInfo = registryInfo.getNecessaryInfo();

            // 是否允许调用自身
            if (Boolean.TRUE.equals(RpcConfigFactory.getInstance().getProtocol().getAutoUseSelf()) && isSelfService(necessaryInfo.getHost(), necessaryInfo.getPort())) {
                nettyInits[i] = NettyInitDto.buildSelf();
                continue;
            }
            nettyInits[i] = NettyInitDto.build(i, necessaryInfo.getPort(), necessaryInfo.getHost(), RpcCallBackFactory.createResponseCallBack(), necessaryInfo.getWeight().intValue());
        }
        try {
            return ClusterFactory.createDefaultConsumerCluster(this.serviceClass, nettyInits);
        } catch (Exception e) {
            LogUtil.error(this, e);
            return null;
        }
    }

    /**
     * 判断是否是自己的服务
     *
     * @param host
     * @param port
     *
     * @return
     */
    private boolean isSelfService(String host, Integer port) {
        return Objects.equals(host, selfIp) && Objects.equals(RpcConfigFactory.getInstance().getProvider().getPort(), port);
    }

}
