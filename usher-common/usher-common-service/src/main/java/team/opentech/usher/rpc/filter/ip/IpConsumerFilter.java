package team.opentech.usher.rpc.filter.ip;

import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.data.AbstractRpcData;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeaderFactory;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ConsumerFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import team.opentech.usher.util.CollectionUtil;
import java.util.Optional;

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
