package team.opentech.usher.rpc.filter.ip;

import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.data.AbstractRpcData;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月20日 11时13分
 */
@RpcSpi
public class IpProviderFilter implements ProviderFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        AbstractRpcData requestData = (AbstractRpcData) invokerContext.getRequestData();
        RpcHeader header = requestData.getHeader(UserInfoHelper.USER_IP_RPC_KEY);

        try {
            if (header != null) {
                UserInfoHelper.setIp(header.getValue());
            }
            return invoker.invoke(invokerContext);
        } finally {
            UserInfoHelper.cleanIp();
        }
    }
}
