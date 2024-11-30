package top.uhyils.usher.rpc.netty;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import top.uhyils.usher.rpc.netty.callback.RpcCallBack;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月20日 14时14分
 */
public abstract class AbstractRpcNetty implements RpcNetty {

    /**
     * 超时时间
     */
    protected Long timeOut;

    /**
     * bootstrap
     */
    protected AbstractBootstrap<?, ? extends Channel> bootstrap;

    /**
     * 当前netty的必要信息
     */
    protected NettyInitDto nettyInitDto;

    protected AbstractRpcNetty() {
    }

    @Override
    public void init(Object... params) throws InterruptedException {
        this.timeOut = (Long) params[0];
        this.nettyInitDto = (NettyInitDto) params[1];
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * 获取rpc回调方法
     *
     * @return
     */
    public RpcCallBack getRpcCallBack() {
        return nettyInitDto.getCallback();
    }
}
