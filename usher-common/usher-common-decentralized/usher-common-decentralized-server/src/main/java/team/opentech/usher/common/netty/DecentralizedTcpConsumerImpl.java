package team.opentech.usher.common.netty;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import java.util.HashMap;
import team.opentech.usher.common.content.UsherDecentralizedContent;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月06日 10时00分
 */
public class DecentralizedTcpConsumerImpl implements DecentralizedConsumer {

    /**
     * bootstrap
     */
    protected AbstractBootstrap<?, ? extends Channel> bootstrap;

    /**
     * 客户端
     */
    private EventLoopGroup group;

    /**
     * 客户端channel
     */
    private ChannelFuture channelFuture;

    private String clusterTypeCode;

    public DecentralizedTcpConsumerImpl(String clusterTypeCode, String host, Integer port) {
        this.clusterTypeCode = clusterTypeCode;
        connectConsumer(host, port);

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
    public Boolean send(byte[] body) throws InterruptedException {
        DecentralizedProtocol decentralizedProtocol = DecentralizedProtocol.build(ByteUtil.subByte(clusterTypeCode.getBytes(UsherDecentralizedContent.DEFAULT_CHARSET), 4), new HashMap<>(), body);
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(decentralizedProtocol.toBytes());
        channelFuture.channel().writeAndFlush(buf);
        return Boolean.TRUE;
    }

    /**
     * 连接consumer
     *
     * @param host
     * @param port
     */
    private void connectConsumer(String host, Integer port) {
        Bootstrap client = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        this.group = eventLoopGroup;
        this.bootstrap = client;
        client.group(eventLoopGroup)
              .channel(NioSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.DEBUG));

        //连接服务器
        this.channelFuture = client.connect(host, port);
    }
}
