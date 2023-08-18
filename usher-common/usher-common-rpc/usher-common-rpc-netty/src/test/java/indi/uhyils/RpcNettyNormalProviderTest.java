package team.opentech.usher;

import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import team.opentech.usher.rpc.factory.RpcBeanFactory;
import team.opentech.usher.rpc.netty.RpcNetty;
import team.opentech.usher.rpc.netty.callback.impl.RpcDefaultRequestCallBack;
import team.opentech.usher.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import team.opentech.usher.rpc.netty.enums.RpcNettyTypeEnum;
import team.opentech.usher.rpc.netty.factory.NettyInitDtoFactory;
import team.opentech.usher.rpc.netty.factory.RpcNettyFactory;
import team.opentech.usher.rpc.netty.function.FunctionOne;
import team.opentech.usher.rpc.netty.function.FunctionOneInterface;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 16时38分
 */
class RpcNettyNormalProviderTest {

    @org.junit.jupiter.api.Test
    void providerInit() throws Exception {
        HashMap<String, Object> beans = new HashMap<>();
        FunctionOneInterface functionOneInterface = new FunctionOne();
        beans.put(functionOneInterface.getClass().getName(), functionOneInterface);
        RpcBeanFactory instance = RpcBeanFactory.getInstance(beans);
        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.PROVIDER, NettyInitDtoFactory.createNettyInitDto("127.0.0.1", 8081, 1, new RpcDefaultRequestCallBack(instance.getRpcBeans())));
        System.out.println("providerStart");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @org.junit.jupiter.api.Test
    void consumerInit() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RpcNetty netty = RpcNettyFactory.createNetty(RpcNettyTypeEnum.CONSUMER, NettyInitDtoFactory.createNettyInitDto("127.0.0.1", 1, 8081, new RpcDefaultResponseCallBack()));
        System.out.println("consumerStart");
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData getHeader = build.createByInfo(9L, null, new RpcHeader[]{rpcHeader}, "team.opentech.usher.rpc.netty.function.FunctionOne", "1", "add", "java.lang.Integer;java.lang.Integer", "[1,2]", "[]");

        assert netty != null;
        RpcData rpcData = netty.sendMsg(getHeader);
        System.out.println("sendOver");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        netty.sendMsg(getHeader);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
