package top.uhyils.usher.rpc.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.AbstractRpcNetty;
import top.uhyils.usher.rpc.netty.callback.ReConnCallBack;
import top.uhyils.usher.rpc.netty.core.handler.RpcConsumerHandler;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.InvokerChainBuilder;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.LastConsumerInvoker;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 15时06分
 */
@RpcSpi(single = false)
public class RpcNettyNormalConsumer extends AbstractRpcNetty implements RpcNettyConsumer {

    /**
     * 客户端
     */
    protected EventLoopGroup group;

    /**
     * 客户端
     */
    protected Bootstrap client;

    /**
     * 客户端
     */
    protected RpcNettySendClient sendClient;

    /**
     * 断线重连成功回调
     */
    protected ReConnCallBack offlineRetrySuccessCallBack;


    @Override
    public void init(Object... params) throws InterruptedException {
        super.init(params);

        this.offlineRetrySuccessCallBack = (ReConnCallBack) params[2];

        this.client = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        this.group = eventLoopGroup;
        this.bootstrap = client;
        this.client.group(eventLoopGroup)
                   .channel(NioSocketChannel.class)
                   .handler(new LoggingHandler(LogLevel.DEBUG))
                   .handler(new ChannelInitializer<NioSocketChannel>() {

                       @Override
                       protected void initChannel(NioSocketChannel ch) {
                           ChannelPipeline p = ch.pipeline();
                           p.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 3, 4, 9, 0));
                           p.addLast("byte-to-object", new RpcConsumerHandler(getRpcCallBack(), sendClient::onResponse));
                           p.addLast("offline-retry-connect", new RpcFibonacciSequenceRetryHandle(RpcNettyNormalConsumer.this));
                       }
                   });

        //连接服务器
        ChannelFuture connect = client.connect(nettyInitDto.getHost(), nettyInitDto.getPort());
        this.sendClient = new RpcNettySendClient(connect);
    }

    public NettyInitDto getNettyInitDto() {
        return this.nettyInitDto;
    }

    @Override
    public Boolean shutdown() {
        try {
            if (group != null) {
                Future<?> future = this.group.shutdownGracefully();
                future.get();
            }
            return Boolean.TRUE;
        } catch (Exception e) {
            LogUtil.error(e, "报错啦.夭寿啊");
            return Boolean.FALSE;
        }
    }

    @Override
    public RpcData sendMsg(RpcData rpcData) throws InterruptedException {
        LastConsumerInvoker lastConsumerInvoker = new LastConsumerInvoker(sendClient);
        RpcInvoker rpcInvoker = InvokerChainBuilder.buildConsumerSendInvokerChain(lastConsumerInvoker);
        FilterContext context = new FilterContext(rpcData);
        return rpcInvoker.invoke(context);
    }

    @Override
    public boolean isActive() {
        if (sendClient == null || sendClient.channelFuture() == null) {
            return false;
        }
        return sendClient.channelFuture().channel().isActive();
    }
}
