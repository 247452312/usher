package team.opentech.usher.protocol.mysql.netty.impl.other;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时12分
 */
public class NettyAdapterTest extends ChannelInitializer<SocketChannel> {


    private final Integer thisPort;

    private final String remoteHost;

    private final Integer remotePort;

    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;

    public NettyAdapterTest(Integer thisPort, String remoteHost, Integer remotePort) {
        this.thisPort = thisPort;
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    public void init() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        LogUtil.info("本地端口开启,端口号:{}", thisPort.toString());
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 1024).childHandler(this);
        ChannelFuture f = b.bind(thisPort).sync();

        f.channel().closeFuture();

    }


    @Override
    protected void initChannel(SocketChannel ch) {
        // 由decoder解析 再交由handler处理
        ch.pipeline().addLast(new NettyHandlerTest(remoteHost, remotePort));
    }

}
