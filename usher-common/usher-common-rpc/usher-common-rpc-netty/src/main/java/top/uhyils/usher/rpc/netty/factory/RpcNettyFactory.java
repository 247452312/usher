package top.uhyils.usher.rpc.netty.factory;

import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.netty.RpcNetty;
import top.uhyils.usher.rpc.netty.callback.ReConnCallBack;
import top.uhyils.usher.rpc.netty.core.RpcSelfNetty;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.rpc.spi.RpcSpiManager;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 13时39分
 */
public class RpcNettyFactory {

    private static final String PROVIDER_NETTY_DEFAULT_NAME = "PROVIDER_NETTY_DEFAULT_NAME";

    private static final String CONSUMER_NETTY_DEFAULT_NAME = "CONSUMER_NETTY_DEFAULT_NAME";


    private static final String PROVIDER_NETTY_SPI_NAME = "provider_netty_spi_name";

    private static final String CONSUMER_NETTY_SPI_NAME = "consumer_netty_spi_name";

    /**
     * 创建一个自己服务里面的netty
     *
     * @param target
     *
     * @return
     */
    public static RpcSelfNetty createSelfNetty(Class<?> target) throws Exception {
        return new RpcSelfNetty(target);
    }

    /**
     * 创建一个服务消费者
     *
     * @param nettyInitDto
     *
     * @return
     */
    public static RpcNetty createConsumer(NettyInitDto nettyInitDto, Long outTime, ReConnCallBack offlineRetrySuccessConsumer) throws InterruptedException {

        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(CONSUMER_NETTY_SPI_NAME, CONSUMER_NETTY_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcNetty) RpcSpiManager.createOrGetExtensionByClass(RpcNetty.class, registryName, outTime, nettyInitDto, offlineRetrySuccessConsumer);
    }

    /**
     * 创建一个服务提供者
     *
     * @param nettyInitDto
     *
     * @return
     */
    public static RpcNetty createProvider(NettyInitDto nettyInitDto, Long outTime) throws InterruptedException {
        // spi 获取消费者的注册者信息
        String registryName = (String) RpcConfigFactory.getCustomOrDefault(PROVIDER_NETTY_SPI_NAME, PROVIDER_NETTY_DEFAULT_NAME);
        // 返回一个构造完成的消费者
        return (RpcNetty) RpcSpiManager.createOrGetExtensionByClass(RpcNetty.class, registryName, outTime, nettyInitDto);
    }
}
