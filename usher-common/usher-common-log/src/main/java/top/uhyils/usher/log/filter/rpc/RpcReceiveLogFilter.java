package top.uhyils.usher.log.filter.rpc;

import com.alibaba.fastjson.JSON;
import java.util.List;
import top.uhyils.usher.context.MyTraceIdContext;
import top.uhyils.usher.pojo.other.RpcTraceInfo;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.data.AbstractRpcData;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeader;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;


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
            MyTraceIdContext.setRpcId(rpcIds);
            MyTraceIdContext.setThraceId(rpcTraceInfo.getTraceId());
        }
        try {
            return invoker.invoke(invokerContext);
        } finally {
            MyTraceIdContext.clean();
        }
    }

}
