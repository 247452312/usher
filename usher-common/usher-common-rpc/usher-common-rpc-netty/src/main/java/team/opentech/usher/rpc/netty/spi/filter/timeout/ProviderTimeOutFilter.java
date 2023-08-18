package team.opentech.usher.rpc.netty.spi.filter.timeout;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.NormalRpcResponseFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月22日 21时11分
 */
@RpcSpi(order = 100)
public class ProviderTimeOutFilter extends AbstractTimeOutFilter implements ProviderFilter {


    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        return invoke0(invoker, invokerContext);
    }

    @Override
    protected RpcData invokeException(RpcData request, Long timeout) throws InterruptedException {
        return RpcFactoryProducer.build(RpcTypeEnum.RESPONSE).createTimeoutResponse(request, timeout);
    }

    @Override
    protected RpcData invokeException(RpcData request, Throwable e) throws InterruptedException {
        NormalRpcResponseFactory build = (NormalRpcResponseFactory) RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        return build.createErrorResponse(request.unique(), e, request.rpcHeaders());
    }

    @Override
    protected Long getTimeout() {
        return RpcConfigFactory.getInstance().getProvider().getTimeout();
    }
}
