package team.opentech.usher.common.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.concurrent.Future;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月26日 09时09分
 */
public class DecentralizedUdpConsumerImpl implements DecentralizedConsumer {

    private EventLoopGroup group;

    private Bootstrap bootstrap;

    public DecentralizedUdpConsumerImpl() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST, true);
    }

    @Override
    public Boolean shutdown() {
        try {
            Future<?> future = group.shutdownGracefully();
            future.get();
            return Boolean.TRUE;
        } catch (ExecutionException | InterruptedException e) {
            LogUtil.error(this, e);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean send(byte[] body) throws InterruptedException {
        Channel channel = bootstrap.bind(0).sync().channel();

        // 像网段内的所有广播机广播UDP消息
        channel.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(body), new InetSocketAddress("255.255.255.255", 8080)));
        channel.close();
        return Boolean.TRUE;
    }

}
