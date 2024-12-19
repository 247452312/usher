package top.uhyils.usher.rpc.netty.spi.filter.invoker;

import java.util.concurrent.TimeoutException;
import top.uhyils.usher.rpc.exception.RpcNetException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.core.RpcNettySendClient;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月21日 21时07分
 */
public class LastConsumerInvoker implements RpcInvoker {

    /**
     * netty
     */
    private RpcNettySendClient client;

    public LastConsumerInvoker(RpcNettySendClient client) {
        this.client = client;
    }

    @Override
    public RpcData invoke(FilterContext context) {
        RpcData request = context.getRequestData();
        LogUtil.debug("请求唯一标示:{},方法:{}", () -> new String[]{request.unique().toString(), request.content().contentString()});
        try {
            return client.send(request);
        } catch (InterruptedException | TimeoutException e) {
            LogUtil.error(this, e);
        }
        throw new RpcNetException();
    }
}
