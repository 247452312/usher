package team.opentech.usher.rpc.cluster.load;

import java.util.Map;
import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;
import org.apache.commons.lang3.RandomUtils;
import team.opentech.usher.rpc.netty.core.RpcNettyConsumer;

/**
 * 随机算法
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 08时51分
 */
@RpcSpi
public class RandomLoadBalanceImpl extends AbstractLoadBalance {

    @Override
    protected int getIndex(SendInfo info, int size) {
        return RandomUtils.nextInt(0, size);
    }

    @Override
    protected int getType() {
        return INDEX_TYPE;
    }
}
