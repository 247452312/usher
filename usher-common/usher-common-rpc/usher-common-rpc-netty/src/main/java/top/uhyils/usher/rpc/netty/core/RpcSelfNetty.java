package top.uhyils.usher.rpc.netty.core;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.factory.RpcBeanFactory;
import top.uhyils.usher.rpc.netty.callback.RpcCallBackFactory;
import top.uhyils.usher.rpc.netty.core.handler.RpcConsumerHandler;
import top.uhyils.usher.rpc.netty.core.handler.RpcProviderHandler;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.LastSelfInvoker;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.rpc.netty.spi.step.RpcStep;
import top.uhyils.usher.rpc.netty.spi.step.template.ConsumerResponseByteExtension;
import top.uhyils.usher.rpc.netty.spi.step.template.ConsumerResponseDataExtension;
import top.uhyils.usher.rpc.netty.spi.step.template.ProviderRequestByteExtension;
import top.uhyils.usher.rpc.spi.RpcSpiManager;

/**
 * 调用自身服务的rpcSelfNetty
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月26日 10时16分
 */
public class RpcSelfNetty<T> implements RpcNettyConsumer {

    private final Class<T> targetClass;

    private final Object service;

    /**
     * 消费者接收回复byte拦截器
     */
    private final List<ConsumerResponseByteExtension> consumerResponseByteFilters;

    /**
     * 消费者接收回复data拦截器
     */
    private final List<ConsumerResponseDataExtension> consumerResponseDataFilters;

    /**
     * 拦截器
     */
    List<ProviderRequestByteExtension> providerRequestByteFilters;


    public RpcSelfNetty(Class<T> targetClass) throws Exception {
        this.targetClass = targetClass;
        this.service = RpcBeanFactory.getInstance().getRpcBeans().get(targetClass.getName());
        providerRequestByteFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ProviderRequestByteExtension.class);
        consumerResponseByteFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ConsumerResponseByteExtension.class);
        consumerResponseDataFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ConsumerResponseDataExtension.class);
    }

    @Override
    public Boolean shutdown() {
        return null;
    }

    @Override
    public RpcData sendMsg(RpcData rpcData) throws InterruptedException {
        LastSelfInvoker lastConsumerInvoker = new LastSelfInvoker(this);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildConsumerSendInvokerChain(lastConsumerInvoker);
        FilterContext context = new FilterContext(rpcData);
        return rpcInvoker.invoke(context);
    }

    /**
     * 执行目标方法
     *
     * @param bytes
     *
     * @return
     *
     * @throws InterruptedException
     */
    public byte[] invoke(byte[] bytes) throws InterruptedException {
        List<ProviderRequestByteExtension> providerRequestByteFilters = new ArrayList<>();
        return RpcProviderHandler.invokeRequestBytes(bytes, providerRequestByteFilters, RpcCallBackFactory.createResponseCallBack());
    }

    /**
     * 获取指定bean
     *
     * @return
     */
    public Object service() {
        return service;
    }

    /**
     * 解析返回值
     *
     * @param invoke
     *
     * @return
     *
     * @throws InterruptedException
     */
    public RpcData parseInvoke(byte[] invoke) throws InterruptedException {
        return RpcConsumerHandler.invokeResponseBytes(invoke, consumerResponseByteFilters, consumerResponseDataFilters, RpcCallBackFactory.createResponseCallBack());
    }


    @Override
    public boolean isActive() {
        // 调用本体不会下线
        return true;
    }
}
