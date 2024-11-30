package top.uhyils.usher.rpc.netty.spi.step.template;

import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcConsumerExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcDataExtension;
import top.uhyils.usher.rpc.netty.spi.step.base.RpcRequestExtension;

/**
 * 消费者,请求,RpcData类型
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月18日 08时17分
 */
public interface ConsumerRequestDataExtension extends RpcConsumerExtension, RpcDataExtension, RpcRequestExtension {


    @Override
    default RpcData doFilter(RpcData data) {
        return data;
    }
}
