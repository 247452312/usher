package top.uhyils.usher.rpc.exchange.pojo.content;

import top.uhyils.usher.rpc.exchange.pojo.content.impl.RpcRequestContentImpl;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;

/**
 * 请求内容装载工厂
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月18日 14时09分
 */
public class RpcRequestContentFactory {


    public static RpcContent createNormalByContentArray(RpcData rpcData, String[] contentArray) {
        return new RpcRequestContentImpl(rpcData, contentArray);
    }
}
