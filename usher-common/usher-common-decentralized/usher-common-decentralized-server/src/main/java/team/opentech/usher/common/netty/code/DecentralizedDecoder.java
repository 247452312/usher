package team.opentech.usher.common.netty.code;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.Objects;
import team.opentech.usher.common.content.UsherDecentralizedContent;
import team.opentech.usher.common.netty.pojo.entity.DecentralizedProtocol;
import team.opentech.usher.common.util.DecentralizedProtocolUtil;
import team.opentech.usher.util.ByteUtil;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年10月11日 09时19分
 */
public class DecentralizedDecoder extends ByteToMessageDecoder {


    /**
     * 集群业务类型唯一标识
     */
    private final String clusterTypeCode;

    public DecentralizedDecoder(String clusterTypeCode) {
        this.clusterTypeCode = clusterTypeCode;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        /**
         * 协议标识+自定义标识==6 如果小于等于6说明半包
         */
        if (in.readableBytes() <= 6) {
            return;
        }

        //如果出现半包问题，回溯
        in.markReaderIndex();

        byte[] titleBytes = new byte[6];
        in.readBytes(titleBytes);
        byte[] bytes = ByteUtil.subByte(titleBytes, 2);
        // 验证title是否匹配
        if (!Objects.equals(new String(bytes, UsherDecentralizedContent.DEFAULT_CHARSET), clusterTypeCode)) {
            // 不匹配的情况下丢弃下整个包
            int length = DecentralizedProtocolUtil.bytesToLength(in);
            int lastLength = in.writerIndex() - in.readerIndex();
            // 半包
            if (lastLength < length) {
                in.resetReaderIndex();
                return;
            }
            in.readBytes(length);
            return;
        }
        if (ByteUtil.equalsFromStart(titleBytes, UsherDecentralizedContent.AGREEMENT_START, UsherDecentralizedContent.AGREEMENT_START.length)) {
            in.resetReaderIndex();
            return;
        }
        // header + body的长度
        int length = DecentralizedProtocolUtil.bytesToLength(in);
        byte[] headerAndBody = new byte[length];
        in.readBytes(headerAndBody);
        DecentralizedProtocol protocol = DecentralizedProtocol.build(titleBytes, headerAndBody);
        if (protocol == null) {
            in.resetReaderIndex();
            return;
        }
        LogUtil.debug(() -> "去中心化集群发来请求: 业务类型:" + new String(protocol.type()) + " header:" + protocol.headerStr() + " body:" + protocol.bodyStr());

        out.add(protocol);
    }

}
