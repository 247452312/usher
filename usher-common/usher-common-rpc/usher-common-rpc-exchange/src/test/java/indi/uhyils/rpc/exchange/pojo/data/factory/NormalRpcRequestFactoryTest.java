package team.opentech.usher.rpc.exchange.pojo.data.factory;

import com.alibaba.fastjson.parser.ParserConfig;
import team.opentech.usher.rpc.config.RpcConfigFactory;
import team.opentech.usher.rpc.enums.RpcStatusEnum;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.content.MyRpcContent;
import team.opentech.usher.rpc.exchange.pojo.content.RpcContent;
import team.opentech.usher.rpc.exchange.pojo.content.impl.RpcRequestContentImpl;
import team.opentech.usher.rpc.exchange.pojo.data.NormalRequestRpcData;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.head.RpcHeader;
import java.nio.charset.StandardCharsets;
import org.junit.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年03月15日 16时43分
 */
public class NormalRpcRequestFactoryTest {

    @Test
    public void createByInfo() throws Exception {
        RpcConfigFactory.setRpcConfig(RpcConfigFactory.newDefault());
        ParserConfig.getGlobalInstance().addAccept("team.opentech.usher.rpc.exchange.pojo.data.factory.Tt");
        NormalRpcRequestFactory normalRpcRequestFactory = new NormalRpcRequestFactory();
        String[] contentArray = new String[]{
            "team.opentech.usher.protocol.rpc.UserProvider",
            "1",
            "login",
            "team.opentech.usher.rpc.exchange.pojo.data.factory.Tt",
            "[{\"@type\":\"team.opentech.usher.rpc.exchange.pojo.data.factory.Tt\",\"name\":\"123456\",\"code\":123456}]",
            "[]"
        };
        // 返回一个构造完成的消费者
        NormalRequestRpcData rpcNormalRequest = new NormalRequestRpcData();
        rpcNormalRequest.setType(RpcTypeEnum.REQUEST.getCode());
        rpcNormalRequest.setVersion(MyRpcContent.VERSION);
        rpcNormalRequest.setHeaders(new RpcHeader[]{new RpcHeader("ke", "va")});
        rpcNormalRequest.setContentArray(contentArray);
        rpcNormalRequest.setStatus(RpcStatusEnum.NULL.getCode());
        rpcNormalRequest.setUnique(3123123123123L);
        RpcContent content = new RpcRequestContentImpl(rpcNormalRequest, contentArray);
        rpcNormalRequest.setContent(content);
        rpcNormalRequest.setSize(content.contentString().getBytes(StandardCharsets.UTF_8).length);

        byte[] bytes = rpcNormalRequest.toBytes();
        RpcData byBytes = normalRpcRequestFactory.createByBytes(bytes);
        int i = 1;
    }

}