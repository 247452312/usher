package top.uhyils.usher.rpc.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import java.io.Serializable;
import top.uhyils.usher.AbstractSendClient;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 10时18分
 */
public class RpcNettySendClient extends AbstractSendClient<RpcData, RpcData> {

    private final ChannelFuture channelFuture;

    public RpcNettySendClient(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public ChannelFuture channelFuture() {
        return channelFuture;
    }

    @Override
    protected Serializable findUniqueFromRequest(RpcData request) {
        return request.unique();
    }

    @Override
    protected Serializable findUniqueFromResponse(RpcData response) {
        return response.unique();
    }

    @Override
    protected boolean doSend(RpcData request) {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(request.toBytes());
        channelFuture.channel().writeAndFlush(buf);
        return Boolean.TRUE;
    }
}
