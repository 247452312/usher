package top.uhyils.usher.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import top.uhyils.usher.pojo.cqe.SdkSqlInvokeCommand;
import top.uhyils.usher.protocol.rpc.GatewayRpcProvider;
import top.uhyils.usher.protocol.rpc.impl.GatewayRpcProviderImpl;
import top.uhyils.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月16日 08时58分
 */
@Component
public class GatewayConfig implements BeanPostProcessor {

    public static final String INVOKE = "invokeRpc";

    public static final String TO_STRING = "toString";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Object o = BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
        if (Objects.equals(beanName, StringUtil.firstToLowerCase(GatewayRpcProviderImpl.class.getSimpleName()))) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new GatewaySdkRpcProviderHandler((GatewayRpcProvider) bean));
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    /**
     * gateway handler
     */
    private static class GatewaySdkRpcProviderHandler implements InvocationHandler {

        private final GatewayRpcProvider gatewayRpcProvider;

        private GatewaySdkRpcProviderHandler(GatewayRpcProvider gatewayRpcProvider) {
            this.gatewayRpcProvider = gatewayRpcProvider;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Objects.equals(method.getName(), TO_STRING)) {
                return "GatewaySdk";
            }
            if (Objects.equals(method.getName(), INVOKE)) {
                return gatewayRpcProvider.invokeRpc((SdkSqlInvokeCommand) args[0]);
            }
            return method.invoke(proxy, args);
        }
    }

}
