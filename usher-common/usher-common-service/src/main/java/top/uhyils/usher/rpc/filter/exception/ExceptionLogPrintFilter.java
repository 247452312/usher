package top.uhyils.usher.rpc.filter.exception;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月15日 12时36分
 */
@RpcSpi(order = 9999)
public class ExceptionLogPrintFilter implements ProviderFilter {


    public ExceptionLogPrintFilter() {
    }

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        try {
            return invoker.invoke(invokerContext);
        } catch (Throwable e) {
            LogUtil.error(e);
            throw e;
        }
    }
}
