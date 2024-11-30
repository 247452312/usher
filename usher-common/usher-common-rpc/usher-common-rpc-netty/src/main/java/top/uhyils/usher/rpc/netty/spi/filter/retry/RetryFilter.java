package top.uhyils.usher.rpc.netty.spi.filter.retry;

import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.config.RpcConfig;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.exception.RpcException;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ConsumerFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月23日 09时10分
 */
@RpcSpi(order = -50)
public class RetryFilter implements ConsumerFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        RpcConfig instance = RpcConfigFactory.getInstance();
        Integer retries = instance.getConsumer().getRetries();
        // 如果出错 重试 次数: 重试次数-1
        for (int i = 0; i < retries - 1; i++) {
            try {
                return invoker.invoke(invokerContext);
            } catch (RpcException e) {
                LogUtil.error("请求出错,等待请求第{}次", String.valueOf(i));
            }
        }
        return invoker.invoke(invokerContext);

    }
}
