package top.uhyils.usher.rpc.netty.spi.step.template;

import top.uhyils.usher.rpc.exchange.pojo.content.RpcRequestContent;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcConsumerExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcResponseExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月20日 09时38分
 */
public interface ProviderResponseObjExtension extends RpcConsumerExtension, RpcResponseExtension {

    /**
     * rpc类扩展点
     *
     * @param obj
     *
     * @return
     */
    Object doFilter(Object obj, RpcRequestContent requestContent);

}
