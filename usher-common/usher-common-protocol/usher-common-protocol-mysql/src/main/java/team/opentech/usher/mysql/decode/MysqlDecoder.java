package team.opentech.usher.mysql.decode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 09时20分
 */
public class MysqlDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] packet = MysqlUtil.readPacket(in);
        if (packet == null) {
            return;
        }
        String dump = MysqlUtil.dump(packet);
        LogUtil.debug("客户端发来请求:\n" + dump);
        out.add(packet);
    }
}
