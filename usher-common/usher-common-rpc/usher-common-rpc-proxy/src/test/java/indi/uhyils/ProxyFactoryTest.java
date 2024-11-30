package top.uhyils.usher;

import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.netty.function.FunctionOneInterface;
import top.uhyils.usher.rpc.proxy.RpcProxyFactory;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月28日 07时05分
 */
class ProxyFactoryTest {

    public static void main(String[] args) throws Throwable {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOne = RpcProxyFactory.newProxy(FunctionOneInterface.class);
        Integer add = functionOne.add(1, 2);
        System.out.println(add);
        for (int i = 0; i < 10; i++) {
            Integer add1 = functionOne.add(1, i);
            System.out.println(add1);
        }
        System.in.read();
    }
}
