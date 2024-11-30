package top.uhyils.usher.rpc.netty.spi.filter.exception;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exception.RpcBusinessException;
import top.uhyils.usher.rpc.exception.RpcException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.rpc.netty.spi.step.template.ProviderResponseExceptionExtension;
import top.uhyils.usher.rpc.netty.spi.step.template.impl.ProviderResponseExceptionExtensionFactory;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月15日 12时36分
 */
@RpcSpi(order = 9000)
public class ExceptionLogTransRespFilter implements ProviderFilter {

    /**
     * 生产者接收请求处理业务异常拦截器
     */
    private final ProviderResponseExceptionExtension providerResponseExceptionFilters;

    public ExceptionLogTransRespFilter() throws InterruptedException {
        providerResponseExceptionFilters = ProviderResponseExceptionExtensionFactory.findResponseExceptionExtension();
    }

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        RpcData rpcData = invokerContext.getRequestData();
        try {
            return invoker.invoke(invokerContext);
        } catch (RpcBusinessException e) {
            return providerResponseExceptionFilters.onBusinessException(rpcData, e);
        } catch (RpcException e) {
            return providerResponseExceptionFilters.onRpcException(rpcData, e);
        } catch (Throwable e) {
            return providerResponseExceptionFilters.onThrowable(rpcData, e);
        }
    }
}
