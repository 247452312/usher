package top.uhyils.usher.rpc.netty.spi.filter.header;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.content.RpcHeaderContext;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeader;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;

/**
 * 动态代码header 获取filter
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月11日 19时13分
 */
@RpcSpi(order = 101)
public class HeaderProviderFilter implements ProviderFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        RpcData requestData = invokerContext.getRequestData();
        RpcHeader[] rpcHeaders = requestData.rpcHeaders();
        for (RpcHeader rpcHeader : rpcHeaders) {
            RpcHeaderContext.addHeader(rpcHeader.getName(), rpcHeader.getValue());
        }
        try {
            return invoker.invoke(invokerContext);
        } finally {
            RpcHeaderContext.remove();
        }
    }
}
