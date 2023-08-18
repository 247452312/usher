package team.opentech.usher.mysql.decode.impl;

import team.opentech.usher.mysql.decode.MysqlDecoder;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.LogUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月22日 09时20分
 */
public class MysqlDecoderImpl extends ByteToMessageDecoder implements MysqlDecoder {

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
