package top.uhyils.usher.rpc.netty.spi.filter.invoker;

import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 08时20分
 */
public interface RpcInvoker {

    /**
     * 执行
     *
     * @param context
     */
    RpcData invoke(FilterContext context) throws InterruptedException;
}
