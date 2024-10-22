package team.opentech.usher.rpc.netty.spi.step.base;


import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.spi.RpcSpiExtension;

/**
 * rpc类扩展点
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年02月14日 13时00分
 */
public interface RpcObjectExtension extends RpcSpiExtension {

    /**
     * rpc类扩展点
     *
     * @param obj
     * @param rpcData
     *
     * @return
     */
    Object doFilter(Object obj, RpcData rpcData);
}
