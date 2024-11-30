package top.uhyils.usher.rpc.netty.callback.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import top.uhyils.usher.rpc.netty.callback.MethodInvoker;

/**
 * 默认 的方法执行器,没有执行的异常
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年07月20日 09时52分
 */
public class DefaultMethodInvoker implements MethodInvoker {

    @Override
    public Object doMethodInvoke(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        return method.invoke(target, args);
    }
}
