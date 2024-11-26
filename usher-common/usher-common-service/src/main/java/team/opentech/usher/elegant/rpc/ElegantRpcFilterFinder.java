package team.opentech.usher.elegant.rpc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import team.opentech.usher.elegant.ElegantHandler;
import team.opentech.usher.elegant.ElegantHandlerFinder;
import team.opentech.usher.rpc.netty.spi.filter.RpcFilter;
import team.opentech.usher.rpc.registry.manager.UsherRpcRegistryManagerFactory;
import team.opentech.usher.rpc.spi.RpcSpiManager;
import team.opentech.usher.util.LogUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 16时25分
 */
public class ElegantRpcFilterFinder implements ElegantHandlerFinder {

    @Override
    public List<ElegantHandler> find() {
        try {
            return Collections.singletonList((ElegantHandler) RpcSpiManager.createOrGetExtensionByClass(RpcFilter.class, "elegantRpcFilter", UsherRpcRegistryManagerFactory.createOrGetUsherRpcRegistryManager()));
        } catch (InterruptedException e) {
            LogUtil.error(this, e);
            return new ArrayList<>();
        }
    }
}
