package top.uhyils.usher.rpc.netty.core;

import top.uhyils.usher.rpc.netty.RpcNetty;

/**
 * rpc的客户端netty执行
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月30日 16时30分
 */
public interface RpcNettyConsumer extends RpcNetty {

    /**
     * 是否活着
     *
     * @return
     */
    boolean isActive();


}
