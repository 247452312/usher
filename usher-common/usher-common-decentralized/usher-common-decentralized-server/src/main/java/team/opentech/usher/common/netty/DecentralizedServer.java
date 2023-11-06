package team.opentech.usher.common.netty;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月03日 15时57分
 */
public interface DecentralizedServer {

    /**
     * 启动去中心化集群监听
     */
    void start() throws InterruptedException;

    /**
     * 停止去中心化集群监听
     *
     * @return
     *
     * @throws InterruptedException
     */
    Boolean shutdown() throws InterruptedException;
}
