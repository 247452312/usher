package team.opentech.usher.rpc.registry;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.cluster.Cluster;
import team.opentech.usher.rpc.cluster.ClusterFactory;
import team.opentech.usher.rpc.config.RpcConfig;
import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.exception.RpcShowDownException;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.registry.mode.ProviderRegistryCenterHandler;
import team.opentech.usher.rpc.registry.mode.RegistryCenterHandler;
import team.opentech.usher.rpc.registry.mode.RegistryCenterHandlerFactory;
import team.opentech.usher.util.LogUtil;
import java.util.HashMap;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年04月23日 13时56分
 */
@RpcSpi(single = false)
public class ProviderRegistryImpl extends AbstractRegistry implements ProviderRegistry {

    private Cluster cluster;

    @Override
    public void init(Object... objects) throws InterruptedException {
        super.init(objects);
        Object bean = objects[1];
        RpcConfig config = RpcConfigFactory.getInstance();
        Integer port = config.getProvider().getPort();
        initCluster(bean, port);
    }

    @Override
    public void allowToPublish() {
        ProviderRegistryCenterHandler registryCenterHandler = (ProviderRegistryCenterHandler) this.registryCenterHandler;
        registryCenterHandler.allowToPublish();
    }

    @Override
    public void notAllowToPublish() {
        ProviderRegistryCenterHandler registryCenterHandler = (ProviderRegistryCenterHandler) this.registryCenterHandler;
        registryCenterHandler.notAllowToPublish();

    }

    @Override
    public Boolean publishStatus() {
        ProviderRegistryCenterHandler registryCenterHandler = (ProviderRegistryCenterHandler) this.registryCenterHandler;
        return registryCenterHandler.isPublish();
    }

    @Override
    public RpcData invoke(RpcData rpcData) throws InterruptedException {
        // provider 应用不会主动发送请求
        return null;
    }

    @Override
    public Boolean close() {
        /*1.关闭与注册中心的连接*/
        try {
            this.registryCenterHandler.close();
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
        /*2.关闭rpc*/
        Boolean shutdown = cluster.shutdown();

        if (!shutdown) {
            throw new RpcShowDownException("rpc关闭错误");
        }
        return true;
    }

    @Override
    protected RegistryCenterHandler doInitRegistryCenterHandler(Object... objects) throws InterruptedException {
        return RegistryCenterHandlerFactory.createProvider(serviceClass);
    }

    /**
     * 初始化生产者对应的集群信息
     *
     * @param bean 对应的服务提供类的bean
     * @param port
     */
    private void initCluster(Object bean, Integer port) {
        HashMap<String, Object> beans = new HashMap<>(1);
        beans.put(this.serviceClass.getName(), bean);
        try {
            this.cluster = ClusterFactory.createDefaultProviderCluster(port, beans);
        } catch (Exception e) {
            LogUtil.error(this, e);
        }
    }
}
