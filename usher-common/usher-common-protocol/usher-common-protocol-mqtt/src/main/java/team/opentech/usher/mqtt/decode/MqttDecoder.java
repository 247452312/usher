package team.opentech.usher.mqtt.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import team.opentech.usher.mqtt.util.MqttUtil;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 09时20分
 */
public class MqttDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] packet = MqttUtil.readPacket(in);
        if (packet == null) {
            return;
        }
        LogUtil.debug(() -> "客户端发来请求:\n" + ByteUtil.dump(packet));
        out.add(packet);
    }
}
