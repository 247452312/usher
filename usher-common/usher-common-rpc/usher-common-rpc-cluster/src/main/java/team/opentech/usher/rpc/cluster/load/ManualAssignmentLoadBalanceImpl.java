package team.opentech.usher.rpc.cluster.load;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.cluster.pojo.NettyInfo;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;
import team.opentech.usher.rpc.netty.RpcNetty;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.apache.commons.lang3.RandomUtils;

/**
 * 手动分配权重
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 08时56分
 */
@RpcSpi
public class ManualAssignmentLoadBalanceImpl extends AbstractLoadBalance {

    /**
     * 权重分配的标记
     */
    private AtomicReferenceArray<NettyInfo> weightArrayForManualAssignment;


    @Override
    public void init(Object... objects) {
        Map<NettyInfo, RpcNetty> nettyMap = (Map<NettyInfo, RpcNetty>) objects[0];
        int length = 0;
        for (NettyInfo nettyInfo : nettyMap.keySet()) {
            length += nettyInfo.getWeight();
        }
        weightArrayForManualAssignment = new AtomicReferenceArray<>(length);
        int writeIndex = 0;
        for (NettyInfo nettyInfo : nettyMap.keySet()) {
            int weight = nettyInfo.getWeight();
            for (int i = 0; i < weight; i++, writeIndex++) {
                weightArrayForManualAssignment.set(writeIndex, nettyInfo);
            }
        }
    }


    @Override
    protected NettyInfo getNettyInfo(SendInfo info, Map<NettyInfo, RpcNetty> nettyMap) {
        int i = RandomUtils.nextInt(0, weightArrayForManualAssignment.length());
        return weightArrayForManualAssignment.get(i);
    }

    @Override
    protected int getType() {
        return NETTY_INFO_TYPE;
    }
}
