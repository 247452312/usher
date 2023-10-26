package team.opentech.usher.common.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月11日 09时14分
 */
public class DecentralizedNetty extends ChannelInitializer<SocketChannel> implements DecentralizedStarter {


    /**
     * 本地端口
     */
    private final Integer port;

    /**
     * 集群业务类型唯一标识
     */
    private final String clusterTypeCode;

    /**
     * 缓存
     */
    private final Redisable redisable;

    private final DecentralizedManager service;

    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;

    public DecentralizedNetty(Integer port, String clusterTypeCode, Redisable redisable, DecentralizedManager service) {
        this.port = port;
        this.clusterTypeCode = clusterTypeCode;
        this.redisable = redisable;
        this.service = service;
    }

    @Override
    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        LogUtil.info("去中心化集群端口开启,端口号:{}", port.toString());
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 1024).childHandler(this);
        ChannelFuture f = b.bind(port).sync();
        f.channel().closeFuture();
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        // 由decoder解析 再交由handler处理
        ch.pipeline().addLast(new DecentralizedDecoder(clusterTypeCode), new DecentralizedHandler(redisable, service));

    }
}
