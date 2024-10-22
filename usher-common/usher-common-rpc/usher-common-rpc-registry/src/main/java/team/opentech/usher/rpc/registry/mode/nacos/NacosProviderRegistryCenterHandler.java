package team.opentech.usher.rpc.registry.mode.nacos;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.config.RegistryConfig;
import team.opentech.usher.rpc.config.RpcConfig;
import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.exception.RpcException;
import team.opentech.usher.rpc.registry.content.RegistryContent;
import team.opentech.usher.rpc.registry.mode.AbstractProviderRegistryCenterHandler;
import team.opentech.usher.rpc.registry.mode.RegistryModeBuilder;
import team.opentech.usher.rpc.registry.pojo.RegistryMetadata;
import team.opentech.usher.rpc.registry.pojo.RegistryModelInfo;
import team.opentech.usher.rpc.registry.pojo.RegistryProviderNecessaryInfo;
import team.opentech.usher.util.LogUtil;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * 生产者注册中心句柄 的nacos默认实现 此类为实现句柄的例子可以通过spi机制修改
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年04月23日 16时50分
 */
@RpcSpi(single = false)
public class NacosProviderRegistryCenterHandler extends AbstractProviderRegistryCenterHandler {


    /**
     * 注册元数据时的key
     */
    private static final String METADATA = "metadata";


    /**
     * nacos的naming
     */
    private final NamingService nacosNaming;

    /**
     * 服务地址
     */
    private String serverAddr;


    /**
     * @throws NacosException
     */
    public NacosProviderRegistryCenterHandler() {
        try {
            RpcConfig rpcConfig = RpcConfigFactory.getInstance();
            RegistryConfig registry = rpcConfig.getRegistry();
            this.serverAddr = registry.getHost() + ":" + registry.getPort();
            nacosNaming = NamingFactory.createNamingService(serverAddr);
        } catch (NacosException e) {
            throw new RpcException(e);
        }
    }

    @Override
    public void close() {
        removeInstance();
        try {
            nacosNaming.shutDown();
        } catch (NacosException e) {
            LogUtil.error(this, e);
            throw new RpcException(e);
        }
        LogUtil.info("rpc生产者关闭:{}", serviceClass.getName());
    }

    @Override
    protected Boolean doRegistry() {
        RegistryModelInfo registryModelInfo = singleProviderRegistryModelInfo();
        //服务端数据转为注册中心数据
        RegistryProviderNecessaryInfo necessaryInfo = registryModelInfo.getNecessaryInfo();
        Instance instance = registryInfoToInstance(registryModelInfo, necessaryInfo);

        try {
            nacosNaming.registerInstance(necessaryInfo.getInterfaceName(), RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, instance);
        } catch (NacosException e) {
            throw new RpcException(e);
        }
        return Boolean.TRUE;
    }

    @Override
    protected void doRemoveRegistryInfo() {
        RegistryModelInfo registryModelInfo = singleProviderRegistryModelInfo();
        RegistryProviderNecessaryInfo providerNecessaryInfo = registryModelInfo.getNecessaryInfo();

        try {
            nacosNaming.deregisterInstance(providerNecessaryInfo.getInterfaceName(), providerNecessaryInfo.getHost(), providerNecessaryInfo.getPort());
        } catch (NacosException e) {
            throw new RpcException(e);
        }
    }

    @Override
    protected Boolean doChangeRegistryInfo() {
        RegistryModelInfo registryModelInfo = singleProviderRegistryModelInfo();
        RegistryProviderNecessaryInfo providerNecessaryInfo = registryModelInfo.getNecessaryInfo();

        try {
            nacosNaming.registerInstance(providerNecessaryInfo.getInterfaceName(), providerNecessaryInfo.getHost(), providerNecessaryInfo.getPort());
        } catch (NacosException e) {
            throw new RpcException(e);
        }
        return Boolean.TRUE;
    }

    @Override
    protected void initRegistryInfo() {
        RegistryModelInfo registryModelInfo = RegistryModeBuilder.initRegistryInfo(serviceClass);
        this.registryModelInfo = Collections.singletonList(registryModelInfo);
    }

    /**
     * provider只有一个model
     *
     * @return
     */
    private RegistryModelInfo singleProviderRegistryModelInfo() {
        return this.registryModelInfo.get(0);
    }

    /**
     * 服务端数据转为注册中心数据
     *
     * @param info
     * @param providerNecessaryInfo
     *
     * @return
     */
    @NotNull
    private Instance registryInfoToInstance(RegistryModelInfo info, RegistryProviderNecessaryInfo providerNecessaryInfo) {
        Instance instance = new Instance();
        instance.setIp(providerNecessaryInfo.getHost());
        instance.setPort(providerNecessaryInfo.getPort());
        instance.setHealthy(providerNecessaryInfo.getHealth());
        instance.setWeight(providerNecessaryInfo.getWeight());
        instance.setServiceName(providerNecessaryInfo.getInterfaceName());
        instance.setClusterName(providerNecessaryInfo.getClusterName());
        instance.setEnabled(providerNecessaryInfo.getEnable());

        Map<String, String> instanceMeta = new HashMap<>(2);
        RegistryMetadata metadata = info.getMetadata();

        instanceMeta.put(METADATA, JSON.toJSONString(metadata, SerializerFeature.WriteClassName));
        instance.setMetadata(instanceMeta);
        return instance;
    }

}
