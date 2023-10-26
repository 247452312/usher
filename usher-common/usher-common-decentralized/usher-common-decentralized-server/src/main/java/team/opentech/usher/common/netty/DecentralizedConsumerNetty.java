package team.opentech.usher.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import java.net.InetSocketAddress;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月26日 09时09分
 */
public class DecentralizedConsumerNetty extends ChannelInitializer<SocketChannel> implements DecentralizedStarter {


    @Override
    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true);
            Channel channel = b.bind(0).sync().channel();

            // 像网段内的所有广播机广播UDP消息
            channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(sendMessage, CharsetUtil.UTF_8),
                                                     new InetSocketAddress("255.255.255.255", 8080)));

            channel.close();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

    }
}
