package team.opentech.usher.rpc.netty.core.handler;

import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.netty.callback.RpcCallBack;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import team.opentech.usher.rpc.netty.spi.filter.invoker.LastProviderInvoker;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import team.opentech.usher.rpc.netty.spi.step.RpcStep;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderRequestByteExtension;
import team.opentech.usher.rpc.spi.RpcSpiManager;
import team.opentech.usher.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月21日 10时58分
 */
public class RpcProviderHandler extends SimpleChannelInboundHandler<ByteBuf> {


    /**
     * 回调
     */
    private final RpcCallBack callback;

    /**
     * 生产者接收请求byte拦截器
     */
    private List<ProviderRequestByteExtension> providerRequestByteFilters;

    public RpcProviderHandler(RpcCallBack callback) {
        this.callback = callback;
        providerRequestByteFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ProviderRequestByteExtension.class);
    }

    public static byte[] invokeRequestBytes(byte[] bytes, List<ProviderRequestByteExtension> providerRequestByteFilters, RpcCallBack callback) throws InterruptedException {
        // ProviderRequestByteFilter
        for (ProviderRequestByteExtension filter : providerRequestByteFilters) {
            bytes = filter.doFilter(bytes);
        }

        /*解析*/
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        // 获取到的Request
        assert build != null;

        RpcData requestRpcData = build.createByBytes(bytes);
        LogUtil.info("接收端接收唯一标示:{}", requestRpcData.unique().toString());
        LastProviderInvoker invoker = new LastProviderInvoker(callback);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildProviderAroundInvokerChain(invoker);
        FilterContext context = new FilterContext(requestRpcData);
        RpcData invoke = rpcInvoker.invoke(context);
        LogUtil.info("接收端发送唯一标示:{}", invoke.unique().toString());
        return invoke.toBytes();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws InterruptedException {
        byte[] bytes = receiveByte(msg);
        byte[] responseBytes = invokeRequestBytes(bytes, providerRequestByteFilters, callback);
        send(ctx, responseBytes);
    }

    private byte[] receiveByte(ByteBuf msg) {
        /*接收并释放byteBuf*/
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        return bytes;
    }

    private void send(ChannelHandlerContext ctx, byte[] responseBytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(responseBytes);
        ctx.channel().writeAndFlush(buf);
    }

}
