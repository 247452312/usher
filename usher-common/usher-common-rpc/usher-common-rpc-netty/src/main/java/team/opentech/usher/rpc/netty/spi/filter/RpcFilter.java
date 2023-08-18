package team.opentech.usher.rpc.netty.spi.filter;

import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import team.opentech.usher.rpc.spi.RpcSpiExtension;

/**
 * handler中执行类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 07时01分
 */
public interface RpcFilter extends RpcSpiExtension {


    /**
     * 执行netty传输过来的逻辑
     *
     * @param invoker
     * @param invokerContext
     */
    RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException;
}
