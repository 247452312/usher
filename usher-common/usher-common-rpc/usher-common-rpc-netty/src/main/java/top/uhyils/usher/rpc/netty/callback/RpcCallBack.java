package top.uhyils.usher.rpc.netty.callback;

import top.uhyils.usher.rpc.exchange.pojo.content.RpcContent;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.pojo.InvokeResult;
import top.uhyils.usher.rpc.spi.RpcSpiExtension;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月23日 18时55分
 */
public interface RpcCallBack extends RpcSpiExtension {


    /**
     * 获取rpc数据
     *
     * @param data
     *
     * @return
     */
    RpcData createRpcData(byte[] data) throws InterruptedException;

    /**
     * 获取rpc体
     *
     * @param data
     *
     * @return
     */
    RpcContent getContent(byte[] data) throws InterruptedException;

    /**
     * 执行方法,下一级去实现此方法
     *
     * @param content
     *
     * @return
     */
    InvokeResult invoke(RpcContent content);


    /**
     * 组装返回值
     *
     * @param unique
     * @param result
     *
     * @return
     */
    RpcData assembly(Long unique, InvokeResult result) throws InterruptedException;

}
