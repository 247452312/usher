package team.opentech.usher.protocol.mqtt.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team.opentech.usher.mqtt.decode.MqttDecoder;
import team.opentech.usher.mqtt.handler.MqttServiceHandler;
import team.opentech.usher.mqtt.netty.MqttNettyHandler;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年09月14日 12时52分
 */
@Component
public class MqttNettyServer extends ChannelInitializer<SocketChannel> {


    @Value("${mqtt-netty:1883}")
    private Integer port;

    /**
     * 主线程,单线程
     */
    private EventLoopGroup bossGroup;

    /**
     * 工作线程,多线程
     */
    private EventLoopGroup workerGroup;

    @Resource
    private MqttServiceHandler mqttServiceHandler;

    @PostConstruct
    public void init() throws InterruptedException {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();

        LogUtil.info("mqtt端口开启,端口号:{}", port.toString());
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
         .option(ChannelOption.SO_BACKLOG, 1024).childHandler(this);
        ChannelFuture f = b.bind(port).sync();

        f.channel().closeFuture();
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        // 由decoder解析 再交由handler处理
        ch.pipeline().addLast(new MqttDecoder(), new MqttNettyHandler(mqttServiceHandler));
    }


}
