package top.uhyils.usher.rpc.cluster.provider;

import top.uhyils.usher.rpc.cluster.Cluster;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年12月25日 12时18分
 */
public abstract class AbstractProviderCluster implements Cluster {

    /**
     * 获取此provider在集群中的位置
     *
     * @return
     */
    public abstract Integer getIndexInColony();


    /**
     * 获取权重
     *
     * @return
     */
    public abstract Float getWeight();


}
