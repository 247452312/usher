package top.uhyils.usher.elegant.rpc;

import top.uhyils.usher.elegant.AbstractElegantHandler;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.spi.filter.FilterContext;
import top.uhyils.usher.rpc.netty.spi.filter.filter.ProviderFilter;
import top.uhyils.usher.rpc.netty.spi.filter.invoker.RpcInvoker;
import top.uhyils.usher.rpc.registry.manager.UsherRpcRegistryManager;
import top.uhyils.usher.util.LogUtil;

/**
 * rpc优雅上下线管理器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月03日 19时05分
 */
@RpcSpi
public class ElegantRpcFilter extends AbstractElegantHandler implements ProviderFilter {

    /**
     * rpcRegistry管理器
     */
    private UsherRpcRegistryManager registryManager;

    public ElegantRpcFilter() throws InterruptedException {

    }

    @Override
    public void init(Object... params) throws InterruptedException {
        ProviderFilter.super.init(params);
        registryManager = (UsherRpcRegistryManager) params[0];
        LogUtil.info("优雅上下线rpcFilter初始化成功");
    }

    @Override
    public RpcData invoke(RpcInvoker invoker, FilterContext invokerContext) throws InterruptedException {

        if (!isOnline()) {
            // todo 这里是否可以报错来代替return
            return null;
        }
        if (Boolean.FALSE.equals(canPublish())) {
            return null;
        }
        newRequest();
        try {
            return invoker.invoke(invokerContext);
        } finally {
            requestOver();
        }
    }

    @Override
    public Boolean isOnline() {
        return registryManager.isPublish();
    }

    @Override
    public void close() {
        doShutdown();
        doClose();
    }

    @Override
    public String name() {
        return "RPC";
    }

    @Override
    public void allowToPublish() {
        super.allowToPublish();
        registryManager.allowProviderToPublish();
    }

    @Override
    public void notAllowToPublish() {
        super.notAllowToPublish();
        registryManager.notAllowProviderToPublish();
    }

    @Override
    protected void doShutdown() {
        registryManager.notAllowProviderToPublish();
    }

    /**
     * 实际关闭需要的业务
     */
    private void doClose() {
        registryManager.closeHook();
    }
}
