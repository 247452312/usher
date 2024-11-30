package top.uhyils.usher.rpc.netty.callback;

import java.lang.reflect.Method;
import top.uhyils.usher.rpc.spi.RpcSpiExtension;

/**
 * 方法执行器,最后一步,通过参数执行方法
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月20日 09时50分
 */
public interface MethodInvoker extends RpcSpiExtension {

    /**
     * 执行方法
     *
     * @param target 执行方法的目标类
     * @param method 方法
     * @param args   参数
     *
     * @return
     */
    Object doMethodInvoke(Object target, Method method, Object[] args) throws Throwable;
}
