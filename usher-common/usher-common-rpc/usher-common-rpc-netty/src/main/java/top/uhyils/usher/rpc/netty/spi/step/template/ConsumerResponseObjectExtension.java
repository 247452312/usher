package top.uhyils.usher.rpc.netty.spi.step.template;

import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcConsumerExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcObjectExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月14日 13时01分
 */
public interface ConsumerResponseObjectExtension extends RpcConsumerExtension, RpcResponseExtension, RpcObjectExtension {

    @Override
    default Object doFilter(Object obj, RpcData rpcData) {
        return obj;
    }
}
