package team.opentech.usher.rpc.netty.spi.step.template;

import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.step.base.RpcDataExtension;
import team.opentech.usher.rpc.netty.spi.step.base.RpcProviderExtension;
import team.opentech.usher.rpc.netty.spi.step.base.RpcRequestExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时21分
 */
public interface ProviderRequestDataExtension extends RpcProviderExtension, RpcRequestExtension, RpcDataExtension {

    @Override
    default RpcData doFilter(RpcData data) {
        return data;
    }
}
