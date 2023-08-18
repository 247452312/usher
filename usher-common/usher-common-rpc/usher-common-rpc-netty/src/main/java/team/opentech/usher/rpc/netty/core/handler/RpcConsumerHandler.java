package team.opentech.usher.rpc.netty.core.handler;

import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.callback.RpcCallBack;
import team.opentech.usher.rpc.netty.core.RpcNettyConsumer;
import team.opentech.usher.rpc.netty.spi.step.RpcStep;
import team.opentech.usher.rpc.netty.spi.step.template.ConsumerResponseByteExtension;
import team.opentech.usher.rpc.netty.spi.step.template.ConsumerResponseDataExtension;
import team.opentech.usher.rpc.spi.RpcSpiManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时58分
 */
public class RpcConsumerHandler extends SimpleChannelInboundHandler<ByteBuf> {

    /**
     * 回调
     */
    private final RpcCallBack callBack;

    /**
     * 观察者模式
     */
    private final RpcNettyConsumer netty;

    /**
     * 消费者接收回复byte拦截器
     */
    private final List<ConsumerResponseByteExtension> consumerResponseByteFilters;

    /**
     * 消费者接收回复data拦截器
     */
    private final List<ConsumerResponseDataExtension> consumerResponseDataFilters;

    public RpcConsumerHandler(RpcCallBack callBack, RpcNettyConsumer netty) {
        this.callBack = callBack;
        this.netty = netty;
        consumerResponseByteFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ConsumerResponseByteExtension.class);
        consumerResponseDataFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ConsumerResponseDataExtension.class);
    }

    /**
     * response 的bytes转RpcData 并且filter
     *
     * @param bytes                       responseRpcData bytes
     * @param consumerResponseByteFilters byte拦截
     * @param consumerResponseDataFilters filter拦截
     * @param callBack
     *
     * @return
     *
     * @throws InterruptedException
     */
    public static RpcData invokeResponseBytes(byte[] bytes, List<ConsumerResponseByteExtension> consumerResponseByteFilters, List<ConsumerResponseDataExtension> consumerResponseDataFilters, RpcCallBack callBack) throws InterruptedException {
        // ConsumerResponseByteFilter
        for (ConsumerResponseByteExtension filter : consumerResponseByteFilters) {
            bytes = filter.doFilter(bytes);
        }
        RpcData rpcData = callBack.createRpcData(bytes);
        // ConsumerResponseDataFilter
        for (ConsumerResponseDataExtension filter : consumerResponseDataFilters) {
            rpcData = filter.doFilter(rpcData);
        }
        return rpcData;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws InterruptedException {
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        RpcData rpcData = invokeResponseBytes(bytes, consumerResponseByteFilters, consumerResponseDataFilters, callBack);
        netty.put(rpcData);
    }
}
