package top.uhyils.usher;

import java.io.IOException;
import java.util.HashMap;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.enums.RpcTypeEnum;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import top.uhyils.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeader;
import top.uhyils.usher.rpc.factory.RpcBeanFactory;
import top.uhyils.usher.rpc.netty.RpcNetty;
import top.uhyils.usher.rpc.netty.callback.ReConnCallBack;
import top.uhyils.usher.rpc.netty.callback.impl.RpcDefaultRequestCallBack;
import top.uhyils.usher.rpc.netty.callback.impl.RpcDefaultResponseCallBack;
import top.uhyils.usher.rpc.netty.factory.RpcNettyFactory;
import top.uhyils.usher.rpc.netty.function.FunctionOne;
import top.uhyils.usher.rpc.netty.function.FunctionOneInterface;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

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
        RpcNetty netty = RpcNettyFactory.createProvider(NettyInitDto.build(1, 8081, "127.0.0.1", new RpcDefaultRequestCallBack(instance.getRpcBeans()), 1), 60 * 60 * 1000L);
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
        RpcNetty netty = RpcNettyFactory.createConsumer(NettyInitDto.build(1, 8081, "127.0.0.1", new RpcDefaultResponseCallBack(), 1), 60 * 60 * 1000L, new ReConnCallBack() {

            @Override
            public void onOffLine(NettyInitDto nettyInitDto) {

            }

            @Override
            public void onReConn(NettyInitDto nettyInitDto) {

            }
        });
        System.out.println("consumerStart");
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData getHeader = build.createByInfo(9L, null, new RpcHeader[]{rpcHeader}, "top.uhyils.usher.rpc.netty.function.FunctionOne", "1", "add", "java.lang.Integer;java.lang.Integer", "[1,2]", "[]");

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
