package team.opentech.usher.protocol.mysql.netty.impl.other;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时14分
 */
public class NettyClientTest {

    private final NettyHandlerTest mysqlInfoHandlerTest;

    private final String remoteHost;

    private final Integer remotePort;

    private ChannelFuture connect;


    public NettyClientTest(NettyHandlerTest mysqlInfoHandlerTest, String remoteHost, Integer mysqlPort) {
        this.mysqlInfoHandlerTest = mysqlInfoHandlerTest;
        this.remoteHost = remoteHost;
        this.remotePort = mysqlPort;
    }


    /**
     * 初始化连接
     *
     * @return
     */
    public void channelActive() {
        LogUtil.info("-------------连接初始化!!!!!!!!!!!");

        Bootstrap client = new Bootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        client.group(eventLoopGroup)
              .channel(NioSocketChannel.class)
              .handler(new LoggingHandler(LogLevel.DEBUG))
              .handler(new ChannelInitializer<NioSocketChannel>() {

                  @Override
                  protected void initChannel(NioSocketChannel ch) {
                      ChannelPipeline p = ch.pipeline();
                      p.addLast("aaa", new TestHandler());
                  }
              });

        //连接服务器
        this.connect = client.connect(remoteHost, remotePort);
    }

    /**
     * 发送信息
     *
     * @param requestMysqlBytes
     *
     * @return
     */
    public void send(byte[] requestMysqlBytes) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(requestMysqlBytes);
        connect.channel().writeAndFlush(buf);
    }


    private class TestHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            mysqlInfoHandlerTest.send(bytes);
        }
    }

}
