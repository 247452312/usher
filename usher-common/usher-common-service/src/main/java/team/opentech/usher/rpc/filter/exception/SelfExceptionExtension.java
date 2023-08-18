package team.opentech.usher.rpc.filter.exception;

import team.opentech.usher.exception.AssertException;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.content.RpcHeaderContext;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exception.RpcBusinessException;
import team.opentech.usher.rpc.exception.RpcException;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.ResponseRpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderResponseExceptionExtension;
import java.util.Map;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月05日 17时48分
 */
@RpcSpi(name = "selfExceptionExtension")
public class SelfExceptionExtension implements ProviderResponseExceptionExtension {

    @Override
    public RpcData onBusinessException(RpcData rpcData, RpcBusinessException e) throws InterruptedException {
        Throwable cause = e.getCause();
        if (cause instanceof AssertException) {
            return ((ResponseRpcFactory) RpcFactoryProducer.build(RpcTypeEnum.RESPONSE)).createAssertExceptionResponse(rpcData, (AssertException) cause);
        }
        return onThrowable(rpcData, cause);
    }

    @Override
    public RpcData onRpcException(RpcData rpcData, RpcException e) throws InterruptedException {
        return ((ResponseRpcFactory) RpcFactoryProducer.build(RpcTypeEnum.RESPONSE)).createErrorResponse(rpcData.unique(), e.getCause(), RpcHeaderContext.get().entrySet().stream().map(t -> new RpcHeader(t.getKey(), t.getValue())).toArray(RpcHeader[]::new));

    }

    @Override
    public RpcData onThrowable(RpcData rpcData, Throwable th) throws InterruptedException {
        ResponseRpcFactory build = (ResponseRpcFactory) RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        Map<String, String> headerMap = RpcHeaderContext.get();
        RpcHeader[] rpcHeaders = new RpcHeader[0];
        if (headerMap != null) {
            rpcHeaders = headerMap.entrySet().stream().map(t -> new RpcHeader(t.getKey(), t.getValue())).toArray(RpcHeader[]::new);
        }
        return build.createErrorResponse(rpcData.unique(), th, rpcHeaders);
    }
}
