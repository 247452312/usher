package top.uhyils.usher.rpc.netty.callback;

import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月12日 14时25分
 */
public interface ReConnCallBack {

    /**
     * 连接断开时
     *
     * @param nettyInitDto
     */
    void onOffLine(NettyInitDto nettyInitDto);

    /**
     * 连接重连时
     *
     * @param nettyInitDto
     */
    void onReConn(NettyInitDto nettyInitDto);

}
