package team.opentech.usher.rpc.registry.mode;

import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.spi.RpcSpiManager;
import team.opentech.usher.util.LogUtil;

/**
 * 注册中心连接器工厂
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月08日 08时50分
 */
public class RegistryCenterHandlerFactory {

    /**
     * 默认的消费者注册中心
     */
    private static final String DEFAULT_CONSUMER_MODE_REGISTRY = "default_consumer_mode_registry";

    /**
     * 配置中消费者注册中心的名称
     */
    private static final String REGISTRY_CONSUMER_MODE_SPI_NAME = "registryConsumerModeSpi";

    /**
     * 默认的生产者注册中心
     */
    private static final String DEFAULT_PROVIDER_MODE_REGISTRY = "default_provider_mode_registry";

    /**
     * 配置中生产者注册中心的名称
     */
    private static final String REGISTRY_PROVIDER_MODE_SPI_NAME = "registryProviderModeSpi";

    private RegistryCenterHandlerFactory() {
    }

    public static ConsumerRegistryCenterHandler createConsumer(Class<?> serviceClass) throws InterruptedException {
        String registryModelName = (String) RpcConfigFactory.getCustomOrDefault(REGISTRY_CONSUMER_MODE_SPI_NAME, DEFAULT_CONSUMER_MODE_REGISTRY);
        ConsumerRegistryCenterHandler orGetExtensionByClass = (ConsumerRegistryCenterHandler) RpcSpiManager.createOrGetExtensionByClass(RegistryCenterHandler.class, registryModelName, serviceClass);
        LogUtil.debug("rpc消费者注册中心句柄创建成功,class:{}", serviceClass.getName());
        return orGetExtensionByClass;
    }

    public static ProviderRegistryCenterHandler createProvider(Class<?> serviceClass) throws InterruptedException {
        String registryModelName = (String) RpcConfigFactory.getCustomOrDefault(REGISTRY_PROVIDER_MODE_SPI_NAME, DEFAULT_PROVIDER_MODE_REGISTRY);
        ProviderRegistryCenterHandler orGetExtensionByClass = (ProviderRegistryCenterHandler) RpcSpiManager.createOrGetExtensionByClass(RegistryCenterHandler.class, registryModelName, serviceClass);
        LogUtil.debug("rpc生产者注册中心句柄创建成功,class:{}", serviceClass.getName());
        return orGetExtensionByClass;
    }
}
