package top.uhyils.usher.rpc.cluster.consumer.impl;

import top.uhyils.usher.rpc.netty.callback.ReConnCallBack;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月12日 12时23分
 */
public class ReConnCallBackImpl implements ReConnCallBack {

    private ConsumerDefaultCluster cluster;

    public ReConnCallBackImpl(ConsumerDefaultCluster cluster) {
        this.cluster = cluster;
    }


    @Override
    public void onOffLine(NettyInitDto nettyInitDto) {
        cluster.onOffLine(nettyInitDto);
    }

    @Override
    public void onReConn(NettyInitDto nettyInitDto) {
        cluster.onReConn(nettyInitDto);
    }
}
