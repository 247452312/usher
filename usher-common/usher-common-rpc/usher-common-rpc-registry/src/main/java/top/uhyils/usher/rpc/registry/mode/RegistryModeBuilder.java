package top.uhyils.usher.rpc.registry.mode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import top.uhyils.usher.rpc.cluster.enums.LoadBalanceEnum;
import top.uhyils.usher.rpc.config.ProviderConfig;
import top.uhyils.usher.rpc.config.RpcConfig;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.exchange.content.UsherRpcContent;
import top.uhyils.usher.rpc.registry.pojo.RegistryMetadata;
import top.uhyils.usher.rpc.registry.pojo.RegistryMetadataOfInterface;
import top.uhyils.usher.rpc.registry.pojo.RegistryMetadataOfMethod;
import top.uhyils.usher.rpc.registry.pojo.RegistryModelInfo;
import top.uhyils.usher.rpc.registry.pojo.RegistryProviderNecessaryInfo;
import top.uhyils.usher.util.IpUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月08日 08时50分
 */
public class RegistryModeBuilder {

    private RegistryModeBuilder() {
    }

    /**
     * 初始化注册信息
     *
     * @param clazz
     *
     * @return
     */
    public static RegistryModelInfo initRegistryInfo(Class<?> clazz) {
        RegistryProviderNecessaryInfo necessaryInfo = initRegistryProviderNecessaryInfo(clazz);
        RegistryMetadata metadata = initRegistryMetadata(clazz);
        return RegistryModelInfo.build(necessaryInfo, metadata);
    }

    /**
     * 初始化注册必要条件
     *
     * @param clazz
     *
     * @return
     */
    private static RegistryProviderNecessaryInfo initRegistryProviderNecessaryInfo(Class<?> clazz) {
        RpcConfig config = RpcConfigFactory.getInstance();
        ProviderConfig provider = config.getProvider();
        Integer port = provider.getPort();
        RegistryProviderNecessaryInfo necessaryInfo = new RegistryProviderNecessaryInfo();
        necessaryInfo.setHost(IpUtil.getIp());
        necessaryInfo.setPort(port);
        necessaryInfo.setRpcVersion(UsherRpcContent.VERSION);
        necessaryInfo.setInterfaceName(clazz.getName());
        necessaryInfo.setHealth(true);
        necessaryInfo.setWeight(20d);
        String clusterName = RpcConfigFactory.getInstance().getApplication().getName();
        necessaryInfo.setClusterName(clusterName);
        // 开启优雅上下线之后 注册时不发布服务
        necessaryInfo.setEnable(provider.isElegant());

        return necessaryInfo;
    }

    /**
     * 元数据初始化
     *
     * @param clazz
     *
     * @return
     */
    private static RegistryMetadata initRegistryMetadata(Class<?> clazz) {
        RegistryMetadata metadata = new RegistryMetadata();

        List<RegistryMetadataOfMethod> methodInfos = initMethodInfo(clazz);
        metadata.setMethodInfos(methodInfos);

        RegistryMetadataOfInterface serviceInfo = initInterface();
        metadata.setServiceInfo(serviceInfo);
        return metadata;
    }

    /**
     * 初始化方法信息
     *
     * @param clazz
     *
     * @return
     */
    private static List<RegistryMetadataOfMethod> initMethodInfo(Class<?> clazz) {
        List<RegistryMetadataOfMethod> methodInfos = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            RegistryMetadataOfMethod metadataOfMethod = new RegistryMetadataOfMethod();
            metadataOfMethod.setName(method.getName());
            metadataOfMethod.setMethodParamTypes(Arrays.stream(method.getParameterTypes()).map(Class::getName).toArray(String[]::new));
            metadataOfMethod.setReturnType(method.getReturnType().getName());
            metadataOfMethod.setUseThisBalance(false);
            metadataOfMethod.setLoadBalance(LoadBalanceEnum.RANDOM.getCode());
            methodInfos.add(metadataOfMethod);
        }
        return methodInfos;
    }

    /**
     * 初始化服务信息
     *
     * @return
     */
    private static RegistryMetadataOfInterface initInterface() {
        RpcConfig config = RpcConfigFactory.getInstance();
        RegistryMetadataOfInterface serviceInfo = new RegistryMetadataOfInterface();
        serviceInfo.setServiceName(config.getApplication().getName());
        serviceInfo.setUseThisBalance(false);
        serviceInfo.setLoadBalance(LoadBalanceEnum.RANDOM.getCode());
        return serviceInfo;
    }
}
