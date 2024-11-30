package top.uhyils.usher.util.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月22日 12时31分
 */
public final class ProxyUtil {

    private ProxyUtil() {
        throw new RuntimeException("util不能被实例化");
    }

    public static <T> T proxy(Class<T> interfaceClass, InvocationHandler target) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, target);
    }

    public static <T extends ProxySelfObserver> T proxyObserver(Class<T> interfaceClass, InvocationHandler target) {
        T t = (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, target);
        t.selfObserver(t);
        return t;
    }

}
