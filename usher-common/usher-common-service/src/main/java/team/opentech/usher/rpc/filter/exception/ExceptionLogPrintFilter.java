package team.opentech.usher.rpc.filter.exception;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import team.opentech.usher.util.LogUtil;

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
