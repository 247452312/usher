package top.uhyils.usher.rpc.cluster.load;

import org.apache.commons.lang3.RandomUtils;
import top.uhyils.usher.rpc.annotation.RpcSpi;
import top.uhyils.usher.rpc.cluster.pojo.SendInfo;

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
