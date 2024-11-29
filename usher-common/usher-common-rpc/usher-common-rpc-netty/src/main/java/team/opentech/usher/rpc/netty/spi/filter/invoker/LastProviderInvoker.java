package team.opentech.usher.rpc.netty.spi.filter.invoker;

import java.util.List;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.NormalRpcResponseFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.netty.callback.RpcCallBack;
import team.opentech.usher.rpc.netty.pojo.InvokeResult;
import team.opentech.usher.rpc.netty.spi.filter.FilterContext;
import team.opentech.usher.rpc.netty.spi.step.RpcStep;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderRequestDataExtension;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderResponseByteExtension;
import team.opentech.usher.rpc.netty.spi.step.template.ProviderResponseDataExtension;
import team.opentech.usher.rpc.spi.RpcSpiManager;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年01月19日 07时27分
 */
public class LastProviderInvoker implements RpcInvoker {

    /**
     * 回调
     */
    private final RpcCallBack callback;


    /**
     * 生产者接收请求data拦截器
     */
    private List<ProviderRequestDataExtension> providerRequestDataFilters;

    /**
     * 生产者接收请求处理完成后的data拦截器
     */
    private List<ProviderResponseDataExtension> providerResponseDataFilters;

    /**
     * 生产者接收请求处理完成后byte拦截器
     */
    private List<ProviderResponseByteExtension> providerResponseByteFilters;


    private NormalRpcResponseFactory rpcResponseFactory;

    public LastProviderInvoker(RpcCallBack callback) throws InterruptedException {
        this.callback = callback;

        providerRequestDataFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ProviderRequestDataExtension.class);
        providerResponseDataFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ProviderResponseDataExtension.class);
        providerResponseByteFilters = RpcSpiManager.createOrGetExtensionListByClassNoInit(RpcStep.class, ProviderResponseByteExtension.class);
        rpcResponseFactory = (NormalRpcResponseFactory) RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
    }

    @Override
    public RpcData invoke(FilterContext context) throws InterruptedException {
        RpcData rpcData = context.getRequestData();
        // ProviderRequestDataFilter
        for (ProviderRequestDataExtension filter : providerRequestDataFilters) {
            rpcData = filter.doFilter(rpcData);
        }
        RpcData assembly = doInvoke(rpcData);
        // ProviderResponseDataFilter
        for (ProviderResponseDataExtension filter : providerResponseDataFilters) {
            assembly = filter.doFilter(assembly);
        }
        byte[] result = assembly.toBytes();
        for (ProviderResponseByteExtension providerResponseByteFilter : providerResponseByteFilters) {
            result = providerResponseByteFilter.doFilter(result);
        }
        return rpcResponseFactory.createByBytes(result);
    }


    /**
     * 执行业务
     *
     * @param rpcData
     *
     * @return
     */
    private RpcData doInvoke(RpcData rpcData) throws InterruptedException {
        InvokeResult invoke = callback.invoke(rpcData.content());
        return callback.assembly(rpcData.unique(), invoke);
    }

}
