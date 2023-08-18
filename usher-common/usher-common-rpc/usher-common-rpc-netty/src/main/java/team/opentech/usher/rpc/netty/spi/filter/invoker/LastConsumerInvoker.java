package team.opentech.usher.rpc.netty.spi.filter.invoker;

import team.opentech.usher.rpc.exception.RpcNetException;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.core.RpcNettyConsumer;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 21时07分
 */
public class LastConsumerInvoker implements RpcInvoker {

    /**
     * netty
     */
    private RpcNettyConsumer netty;

    public LastConsumerInvoker(RpcNettyConsumer netty) {
        this.netty = netty;
    }

    @Override
    public RpcData invoke(FilterContext context) {
        RpcData request = context.getRequestData();
        LogUtil.info("请求唯一标示:{},方法:{}", request.unique().toString(), request.content().contentString());

        if (netty.sendMsg(request.toBytes())) {
            return netty.wait(request.unique());
        }
        throw new RpcNetException();
    }
}
