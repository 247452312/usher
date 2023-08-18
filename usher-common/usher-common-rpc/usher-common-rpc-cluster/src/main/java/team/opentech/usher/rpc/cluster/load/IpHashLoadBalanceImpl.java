package team.opentech.usher.rpc.cluster.load;

import team.opentech.usher.rpc.annotation.RpcSpi;
import team.opentech.usher.rpc.cluster.pojo.SendInfo;


/**
 * 根据ip指定
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年06月10日 08时53分
 */
@RpcSpi
public class IpHashLoadBalanceImpl extends AbstractLoadBalance {

    @Override
    protected int getIndex(SendInfo info, int size) {
        return info.getIp().hashCode();
    }

    @Override
    protected int getType() {
        return INDEX_TYPE;
    }
}
