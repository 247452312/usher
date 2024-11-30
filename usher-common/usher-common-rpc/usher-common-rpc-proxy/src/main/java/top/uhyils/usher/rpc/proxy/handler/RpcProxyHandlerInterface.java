package top.uhyils.usher.rpc.proxy.handler;

import java.lang.reflect.InvocationHandler;
import top.uhyils.usher.rpc.spi.RpcSpiExtension;

/**
 * rpc的interface扩展点
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月01日 08时23分
 */
public interface RpcProxyHandlerInterface extends InvocationHandler, RpcSpiExtension {


}
