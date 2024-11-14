package team.opentech.usher.protocol.mysql.netty.impl.other;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import java.net.InetSocketAddress;
import team.opentech.usher.mysql.decode.MysqlDecoder;
import team.opentech.usher.mysql.netty.MysqlInfoHandler;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时15分
 */
public class MysqlInfoHandlerTest extends MysqlInfoHandler implements ChannelInboundHandler {


    private final String mysqlHost;

    private final Integer mysqlPort;

    private MysqlNettyClientTest mysqlNettyClientTest;

    /**
     * 连接
     */
    private Channel mysqlChannel;


    public MysqlInfoHandlerTest(String mysqlHost, Integer mysqlPort) {
        this.mysqlHost = mysqlHost;
        this.mysqlPort = mysqlPort;
    }

    /**
     * 初始化连接
     *
     * @param ctx
     *
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.mysqlChannel = ctx.channel();
        mysqlNettyClientTest = new MysqlNettyClientTest(this, mysqlHost, mysqlPort);
        //入口连接
        InetSocketAddress inetSocketAddress = (InetSocketAddress) mysqlChannel.localAddress();
        LogUtil.info("mysql 连接!" + inetSocketAddress);

        mysqlNettyClientTest.channelActive();
    }

    /**
     * 接收到信息时调用
     *
     * @param ctx
     * @param msg
     *
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);

        /**
         * 因{@link MysqlDecoder#decode} 所以这里一定为byte[]
         */
        byte[] requestMysqlBytes = (byte[]) msg;
        mysqlNettyClientTest.send(requestMysqlBytes);
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    public void send(byte[] msg) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(msg);
        this.mysqlChannel.writeAndFlush(buf);
    }


}
