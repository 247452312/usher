package top.uhyils.usher;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import top.uhyils.usher.rpc.annotation.UsherRpc;
import top.uhyils.usher.rpc.config.RpcConfigFactory;
import top.uhyils.usher.rpc.enums.RpcTypeEnum;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import top.uhyils.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import top.uhyils.usher.rpc.exchange.pojo.head.RpcHeader;
import top.uhyils.usher.rpc.netty.function.FunctionOne;
import top.uhyils.usher.rpc.netty.function.FunctionOneInterface;
import top.uhyils.usher.rpc.registry.Registry;
import top.uhyils.usher.rpc.registry.RegistryFactory;
import top.uhyils.usher.rpc.registry.content.RegistryContent;
import top.uhyils.usher.rpc.registry.mode.RegistryCenterHandler;
import top.uhyils.usher.rpc.registry.mode.nacos.NacosConsumerRegistryCenterHandler;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月27日 16时20分
 */
@UsherRpc
class RegistryFactoryTest {

    @Test
    void createConsumer() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        RegistryCenterHandler mode = new NacosConsumerRegistryCenterHandler();
        Class<FunctionOneInterface> clazz = FunctionOneInterface.class;
        Registry consumer = RegistryFactory.createConsumer(clazz);
        RpcData add = consumer.invoke(initRpcData(90L, clazz, "add", (Class<FunctionOneInterface>[]) clazz.getMethods()[0].getParameterTypes(), new Object[]{1, 2}));

        System.out.println("--------------------------------------------------------------------" + add);

        for (int i = 0; i < 10; i++) {
            RpcData add1 = consumer.invoke(initRpcData(Integer.toUnsignedLong(i), clazz, "add", (Class<FunctionOneInterface>[]) clazz.getMethods()[0].getParameterTypes(), new Object[]{1, 2}));
            System.out.println("-------------重复执行 这里是" + i + "遍结果是:   " + add1);

        }
        Assert.isTrue(true, "hello world");
    }

    @Test
    void createProvider1() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Registry provider = RegistryFactory.createProvider(FunctionOneInterface.class, functionOneInterface);
        System.out.println("服务提供者服务加载完毕----------------------------!!!!!!! yeah");
        System.in.read();

        Assert.isTrue(true, "hello world");
    }

    @Test
    void createProvider2() throws Exception {

        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        FunctionOneInterface functionOneInterface = new FunctionOne();
        Registry provider = RegistryFactory.createProvider(FunctionOneInterface.class, functionOneInterface);
        System.out.println("服务提供者服务加载完毕----------------------------!!!!!!! yeah");
        System.in.read();

        Assert.isTrue(true, "hello world");
    }

    @Test
    void testNacos() throws Exception {
        NamingService namingService = NamingFactory.createNamingService("192.168.1.101:8848");
        namingService.subscribe("top.uhyils.usher.rpc.netty.function.FunctionOneInterface", RegistryContent.DEFAULT_REGISTRY_GROUP_NAME, event -> {
            if (event instanceof NamingEvent) {
                System.out.println(((NamingEvent) event).getServiceName());
                System.out.println(((NamingEvent) event).getGroupName());
                System.out.println(((NamingEvent) event).getInstances());
                System.out.println(((NamingEvent) event).getClusters());
            }
        });
        System.in.read();

        Assert.isTrue(true, "hello world");
    }

    /**
     * 初始化rpcData
     *
     * @param unique
     * @param clazz
     * @param methodName
     * @param paramType
     * @param args
     *
     * @return
     */
    private RpcData initRpcData(Long unique, Class clazz, String methodName, Class[] paramType, Object[] args) throws InterruptedException {
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        // header具体发送什么还没有确定
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("default_value");
        rpcHeader.setValue("value");

        // 类型的返回值
        String paramTypeStr = parseParamTypeToStr(paramType);

        assert build != null;

        return build.createByInfo(unique, null, new RpcHeader[]{rpcHeader}, clazz.getName(), "1", methodName, paramTypeStr, JSON.toJSONString(args), "[]");
    }

    /**
     * 获取paramType的字符串
     *
     * @param paramType
     *
     * @return
     */
    private String parseParamTypeToStr(Class[] paramType) {
        StringBuilder sb = new StringBuilder();
        for (Class<?> paramTypeClass : paramType) {
            sb.append(paramTypeClass.getName());
            sb.append(";");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }
}
