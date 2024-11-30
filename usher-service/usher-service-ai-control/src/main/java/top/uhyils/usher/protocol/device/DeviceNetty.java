package top.uhyils.usher.protocol.device;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月23日 15时02分
 */
@Component
public class DeviceNetty {


    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;

    @Value("${ai.device.protocol.port:1996}")
    private Integer port;


    public void init() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
         .channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 100)
         .handler(new LoggingHandler(LogLevel.DEBUG))
         .childHandler(new ChannelInitializer<SocketChannel>() {

             @Override
             public void initChannel(SocketChannel ch) {
                 ChannelPipeline p = ch.pipeline();
                 // 1.分析数据类型属于哪一种协议
                 // 2.根据这种协议来解析数据
                 // 3.将数据转换为指令
                 // 4.将指令发送到核心处理组件
                 // 5. 无状态协议就断开 有状态协议需要管理
                 p.addLast("length-decoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 3, 4, 9, 0));
             }
         });
        b.bind(port).sync();
    }
}
