package top.uhyils.usher.rpc.netty.spi.step.template;

import top.uhyils.usher.rpc.netty.spi.step.base.RpcByteExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcConsumerExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcRequestExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时18分
 */
public interface ConsumerRequestByteExtension extends RpcConsumerExtension, RpcByteExtension, RpcRequestExtension {

    @Override
    default byte[] doFilter(byte[] bytes) {
        return bytes;
    }
}
