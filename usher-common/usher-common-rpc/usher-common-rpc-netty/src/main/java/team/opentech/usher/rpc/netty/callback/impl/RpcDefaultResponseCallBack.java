package team.opentech.usher.rpc.netty.callback.impl;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.enums.RpcTypeEnum;
import team.opentech.usher.rpc.exchange.pojo.content.RpcContent;
import team.opentech.usher.rpc.exchange.pojo.data.RpcData;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactory;
import team.opentech.usher.rpc.exchange.pojo.data.factory.RpcFactoryProducer;
import team.opentech.usher.rpc.netty.callback.RpcCallBack;
import team.opentech.usher.rpc.netty.pojo.InvokeResult;

/**
 * 这个只是一个样例,具体如何执行要看下一级
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 19时15分
 */
@RpcSpi
public class RpcDefaultResponseCallBack implements RpcCallBack {

    @Override
    public RpcData createRpcData(byte[] data) throws InterruptedException {
        /*解析*/
        RpcFactory build = RpcFactoryProducer.build(RpcTypeEnum.RESPONSE);
        // 获取到的Request
        assert build != null;
        return build.createByBytes(data);
    }

    @Override
    public RpcContent getContent(byte[] data) throws InterruptedException {
        RpcData request = createRpcData(data);
        return request.content();
    }

    @Override
    public InvokeResult invoke(RpcContent content) {
        /*todo 这里之后要加入 服务端和客户端多次通信的情况*/
        /*if (content instanceof RpcResponseContent) {
            RpcResponseContent requestContent = (RpcResponseContent) content;
            String responseContent = requestContent.getResponseContent();
            return null;
        }*/
        return null;
    }

    @Override
    public RpcData assembly(Long unique, InvokeResult result) {
        return null;
    }


}
