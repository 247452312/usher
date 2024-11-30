package top.uhyils.usher.rpc.netty.spi.step.template;

import top.uhyils.usher.rpc.netty.spi.step.base.RpcByteExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcProviderExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcRequestExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时21分
 */
public interface ProviderRequestByteExtension extends RpcProviderExtension, RpcRequestExtension, RpcByteExtension {

    @Override
    default byte[] doFilter(byte[] bytes) {
        return bytes;
    }
}
