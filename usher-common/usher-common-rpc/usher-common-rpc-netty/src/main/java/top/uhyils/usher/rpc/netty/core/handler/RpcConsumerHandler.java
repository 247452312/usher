package top.uhyils.usher.rpc.netty.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;
import java.util.function.Consumer;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.callback.RpcCallBack;
import top.uhyils.usher.rpc.netty.spi.step.RpcStep;
import top.uhyils.usher.rpc.netty.spi.step.template.ConsumerResponseByteExtension;
import top.uhyils.usher.rpc.netty.spi.step.template.ConsumerResponseDataExtension;
import top.uhyils.usher.rpc.spi.RpcSpiManager;

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
     * 消息来时回调
     */
    private final Consumer<RpcData> onMessage;

    /**
     * 消费者接收回复byte拦截器
     */
    private final List<ConsumerResponseByteExtension> consumerResponseByteFilters;

    /**
     * 消费者接收回复data拦截器
     */
    private final List<ConsumerResponseDataExtension> consumerResponseDataFilters;

    public RpcConsumerHandler(RpcCallBack callBack, Consumer<RpcData> onMessage) {
        this.callBack = callBack;
        this.onMessage = onMessage;
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
        onMessage.accept(rpcData);
    }
}
