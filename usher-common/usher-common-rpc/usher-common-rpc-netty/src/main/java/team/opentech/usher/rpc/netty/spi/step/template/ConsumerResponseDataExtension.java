package team.opentech.usher.rpc.netty.spi.step.template;

import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.netty.spi.step.base.RpcConsumerExtension;
import team.opentech.usher.rpc.netty.spi.step.base.RpcDataExtension;
import team.opentech.usher.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时23分
 */
public interface ConsumerResponseDataExtension extends RpcConsumerExtension, RpcResponseExtension, RpcDataExtension {

    /**
     * 子类可引用此方法
     *
     * @param data
     *
     * @return
     */
    @Override
    default RpcData doFilter(RpcData data) {
        return data;
    }
}
