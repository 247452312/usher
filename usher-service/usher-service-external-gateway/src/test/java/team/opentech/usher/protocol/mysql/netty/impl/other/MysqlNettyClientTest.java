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
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时14分
 */
public class MysqlNettyClientTest {

    private final MysqlInfoHandlerTest mysqlInfoHandlerTest;

    private final String mysqlHost;

    private final Integer mysqlPort;

    private ChannelFuture connect;


    public MysqlNettyClientTest(MysqlInfoHandlerTest mysqlInfoHandlerTest, String mysqlHost, Integer mysqlPort) {
        this.mysqlInfoHandlerTest = mysqlInfoHandlerTest;
        this.mysqlHost = mysqlHost;
        this.mysqlPort = mysqlPort;
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
        this.connect = client.connect(mysqlHost, mysqlPort);
    }

    /**
     * 发送信息
     *
     * @param requestMysqlBytes
     *
     * @return
     */
    public void send(byte[] requestMysqlBytes) {
        LogUtil.info("client向mysql发送信息:\n" + MysqlUtil.dump(requestMysqlBytes));
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(requestMysqlBytes);
        connect.channel().writeAndFlush(buf);
    }


    private class TestHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

            byte[] bytes = new byte[msg.readableBytes()];
            msg.readBytes(bytes);
            LogUtil.info("mysql向client发送信息:\n" + MysqlUtil.dump(bytes));
            mysqlInfoHandlerTest.send(bytes);
        }
    }

}
