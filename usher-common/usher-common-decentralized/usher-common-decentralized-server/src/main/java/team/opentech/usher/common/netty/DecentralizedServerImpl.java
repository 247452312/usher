package team.opentech.usher.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.common.netty.code.DecentralizedDecoder;
import team.opentech.usher.common.netty.handler.DecentralizedHandler;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月11日 09时14分
 */
public class DecentralizedServerImpl extends ChannelInitializer<SocketChannel> implements DecentralizedServer {


    private final DecentralizedManager service;

    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;


    public DecentralizedServerImpl(DecentralizedManager service) {
        this.service = service;
    }

    @Override
    public void start() throws InterruptedException {
        UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        Integer port = instance.serverPort();
        LogUtil.info("去中心化集群端口开启,端口号:{}", port.toString());
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 1024)
         // 开启接收udp信息
         .option(ChannelOption.SO_BROADCAST, true)
         .childHandler(this);
        ChannelFuture f = b.bind(port).sync();
        f.channel().closeFuture();
    }

    @Override
    public Boolean shutdown() throws InterruptedException {
        try {
            Future<?> future = bossGroup.shutdownGracefully();
            future.get();
            return Boolean.TRUE;
        } catch (ExecutionException e) {
            LogUtil.error(this, e);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isOnline() {
        return !bossGroup.isShutdown();
    }

    @Override
    public void close() {
        bossGroup.shutdownGracefully(0, 0, TimeUnit.SECONDS);
    }


    @Override
    protected void initChannel(SocketChannel ch) {
        UsherDecentralizedContext instance = UsherDecentralizedContext.getInstance();
        // 由decoder解析 再交由handler处理
        ch.pipeline().addLast(new DecentralizedDecoder(instance.clusterTypeCode()), new DecentralizedHandler(service));

    }
}
