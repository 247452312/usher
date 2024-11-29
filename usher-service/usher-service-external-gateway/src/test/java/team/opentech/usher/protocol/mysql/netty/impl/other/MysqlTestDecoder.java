package team.opentech.usher.protocol.mysql.netty.impl.other;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import team.opentech.usher.mysql.decode.MysqlDecoder;
import team.opentech.usher.mysql.util.MysqlUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 15时03分
 */
public class MysqlTestDecoder extends MysqlDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        byte[] packet = MysqlUtil.readPacket(in);
        if (packet == null) {
            return;
        }
        out.add(packet);
    }
}
