package team.opentech.usher.log.filter.rpc;

import com.alibaba.fastjson.JSON;
import team.opentech.usher.context.MyTraceIdContext;
import team.opentech.usher.enums.LogTypeEnum;
import team.opentech.usher.pojo.other.RpcTraceInfo;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.exception.RpcException;
import team.opentech.usher.rpc.exchange.enums.RpcRequestContentEnum;
import team.opentech.usher.rpc.exchange.pojo.content.RpcContent;
import team.opentech.usher.rpc.exchange.pojo.data.AbstractRpcData;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeaderFactory;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.filter.filter.ConsumerFilter;
import team.opentech.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import team.opentech.usher.util.SupplierWithException;
import java.util.List;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月23日 14时53分
 */
@RpcSpi
public class RpcSendLogFilter implements ConsumerFilter {

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) {
        // 优先获取rpcId,防止thisRpcId提前+1
        List<Integer> nextRpcIds = MyTraceIdContext.getNextTraceIds();
        AbstractRpcData requestData = (AbstractRpcData) invokerContext.getRequestData();
        RpcContent content = requestData.content();
        SupplierWithException<RpcData> rpcDataSupplierWithException = () -> {
            addRpcTraceInfoToRpcHeader(requestData, nextRpcIds);
            return invoker.invoke(invokerContext);
        };
        try {
            return MyTraceIdContext.printLogInfo(LogTypeEnum.RPC, rpcDataSupplierWithException, new String[]{parseContentInfo(content, RpcRequestContentEnum.SERVICE_NAME),
                                                     parseContentInfo(content, RpcRequestContentEnum.METHOD_NAME)}, parseContentInfo(content, RpcRequestContentEnum.SERVICE_NAME),
                                                 parseContentInfo(content, RpcRequestContentEnum.METHOD_NAME));
        } catch (Throwable throwable) {
            throw new RpcException(throwable);
        }
    }

    private String parseContentInfo(RpcContent content, RpcRequestContentEnum methodName) {
        return content.getLine(methodName.getLine());
    }

    private void addRpcTraceInfoToRpcHeader(AbstractRpcData requestData, List<Integer> nextRpcIds) {
        RpcTraceInfo rpcTraceInfo = RpcTraceInfo.build(MyTraceIdContext.getThraceId(), nextRpcIds);

        RpcHeader[] rpcHeaders = requestData.rpcHeaders();
        int rpcHeaderLength = rpcHeaders.length;
        RpcHeader[] newRpcHeaders = new RpcHeader[rpcHeaderLength + 1];
        System.arraycopy(rpcHeaders, 0, newRpcHeaders, 0, rpcHeaderLength);
        newRpcHeaders[rpcHeaderLength] = RpcHeaderFactory.newHeader(MyTraceIdContext.RPC_HEADER_TRACE_INFO, JSON.toJSONString(rpcTraceInfo));
        requestData.setHeaders(newRpcHeaders);
    }
}
