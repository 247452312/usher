package team.opentech.usher.rpc.netty;

import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月19日 10时22分
 */
class TestTest {

    @org.junit.jupiter.api.Test
    void testRequest() throws Exception {
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.REQUEST);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData getHeader = build.createByInfo(9L, null, new RpcHeader[]{rpcHeader}, "team.opentech.usher.rpc.netty.RpcHeader", "1", "getHeader", "", "[]", "[]");
        byte[] bytes = getHeader.toBytes();
        RpcData byBytes = build.createByBytes(bytes);
        assert byBytes != null;
    }

    @org.junit.jupiter.api.Test
    void testResponse() throws Exception {
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        RpcHeader rpcHeader = new RpcHeader();
        rpcHeader.setName("a");
        rpcHeader.setValue("b");
        assert build != null;
        RpcData response = build.createByInfo(9L, new Object[]{(byte) 10}, new RpcHeader[]{rpcHeader}, "1", "{\"a\":\"b\"}");

        byte[] bytes = response.toBytes();
        RpcData byBytes = build.createByBytes(bytes);
        assert byBytes != null;
    }
}
