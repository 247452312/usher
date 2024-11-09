package team.opentech.usher.log.filter.rpc;

import com.alibaba.fastjson.JSON;
import team.opentech.usher.context.MyTraceIdContext;
import team.opentech.usher.pojo.other.RpcTraceInfo;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exchange.pojo.data.AbstractRpcData;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import java.util.List;


/**
 * rpcTrace信息接收
 * 注: order排在timeOutFilter后面
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月23日 14时53分
 */
@RpcSpi(order = 101)
public class RpcReceiveLogFilter implements ProviderFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {
        AbstractRpcData requestData = (AbstractRpcData) invokerContext.getRequestData();
        RpcHeader traceInfo = requestData.getHeader(MyTraceIdContext.RPC_HEADER_TRACE_INFO);
        if (traceInfo != null) {
            RpcTraceInfo rpcTraceInfo = JSON.parseObject(traceInfo.getValue(), RpcTraceInfo.class);
            List<Integer> rpcIds = rpcTraceInfo.getRpcIds();
            MyTraceIdContext.setTraceId(rpcIds);
            MyTraceIdContext.setThraceId(rpcTraceInfo.getTraceId());
        }
        try {
            return invoker.invoke(invokerContext);
        } finally {
            MyTraceIdContext.clean();
        }
    }

}
