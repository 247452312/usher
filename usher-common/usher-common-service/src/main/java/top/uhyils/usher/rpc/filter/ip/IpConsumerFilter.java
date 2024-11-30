package top.uhyils.usher.rpc.filter.ip;

import java.util.Optional;
import top.uhyils.usher.context.UserInfoHelper;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.data.AbstractRpcData;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeader;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeaderFactory;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ConsumerFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.util.CollectionUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月20日 11时08分
 */
@RpcSpi
public class IpConsumerFilter implements ConsumerFilter {


    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        Optional<String> ipOpt = UserInfoHelper.getUserIp();
        ipOpt.ifPresent(ip -> {
            AbstractRpcData requestData = (AbstractRpcData) invokerContext.getRequestData();
            RpcHeader[] rpcHeaders = requestData.rpcHeaders();
            rpcHeaders = CollectionUtil.arrayAdd(rpcHeaders, RpcHeaderFactory.newHeader(UserInfoHelper.USER_IP_RPC_KEY, ip));
            requestData.setHeaders(rpcHeaders);
        });
        return invoker.invoke(invokerContext);
    }
}
