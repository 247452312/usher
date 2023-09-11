package team.opentech.usher.protocol.mysql.netty.impl.other;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时15分
 */
public class NettyHandlerTest extends ChannelInboundHandlerAdapter implements ChannelInboundHandler {


    private final String remoteHost;

    private final Integer remotePort;

    private NettyClientTest mysqlNettyClientTest;

    /**
     * 连接
     */
    private Channel channel;


    public NettyHandlerTest(String mysqlHost, Integer mysqlPort) {
        this.remoteHost = mysqlHost;
        this.remotePort = mysqlPort;
    }

    /**
     * 初始化连接
     *
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.channel = ctx.channel();
        mysqlNettyClientTest = new NettyClientTest(this, remoteHost, remotePort);
        //入口连接
        InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.localAddress();
        LogUtil.info("连接!" + inetSocketAddress);

        mysqlNettyClientTest.channelActive();
    }

    /**
     * 接收到信息时调用
     *
     * @param ctx
     * @param msg
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf request = (ByteBuf) msg;
        byte[] bytes = new byte[request.readableBytes()];
        request.readBytes(bytes);
        LogUtil.info("client->server:\n" + ByteUtil.dump(bytes));
        mysqlNettyClientTest.send(bytes);
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    public void send(byte[] msg) {
        LogUtil.info("server->client:\n" + ByteUtil.dump(msg));
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg);
        this.channel.writeAndFlush(buf);
    }


}
