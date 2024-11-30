package top.uhyils.usher.rpc.cluster;

import java.util.List;
import java.util.Map;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.rpc.cluster.enums.LoadBalanceEnum;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;
import top.uhyils.usher.rpc.exchange.pojo.data.RpcData;
import top.uhyils.usher.rpc.netty.RpcNetty;
import top.uhyils.usher.rpc.netty.pojo.NettyInitDto;
import top.uhyils.usher.rpc.spi.RpcSpiExtension;

/**
 * 服务集群
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 09时29分
 */
public interface Cluster extends RpcSpiExtension {

    /**
     * 获取接口名称
     *
     * @return
     */
    String getInterfaceName();

    /**
     * 获取负载均衡方式
     *
     * @return
     */
    LoadBalanceEnum getTypeOfLoadBalance();

    /**
     * 获取cluster中是否是单例,例如 provider就一定是单例
     *
     * @return
     */
    Boolean isSingle();

    /**
     * 获取集群数量
     *
     * @return
     */
    Integer getNumOfColony();


    /**
     * 获取此cluster下所有的netty
     *
     * @return
     */
    Map<NettyInitDto, RpcNetty> getAllNetty();


    /**
     * 关闭,不会立即关闭.会等待线程结束
     *
     * @return
     */
    Boolean shutdown();

    /**
     * 发送信息
     *
     * @param rpcData
     * @param info
     *
     * @return
     *
     * @throws InterruptedException
     */
    @NotNull
    RpcData sendMsg(RpcData rpcData, SendInfo info) throws InterruptedException;

    /**
     * 服务数量改变时->生产者不需要关心自己的上下线,所以只有消费者需要完成逻辑
     *
     * @param nettyInfos
     *
     * @return
     */
    Boolean onServiceStatusChange(List<NettyInitDto> nettyInfos);

    /**
     * 某个连接断开时
     *
     * @param nettyInitDto
     */
    void onOffLine(NettyInitDto nettyInitDto);

    /**
     * 某个连接重连时
     *
     * @param nettyInitDto
     */
    void onReConn(NettyInitDto nettyInitDto);

}
