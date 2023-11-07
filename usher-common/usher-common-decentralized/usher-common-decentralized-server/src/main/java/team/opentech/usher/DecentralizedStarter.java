package team.opentech.usher;

import team.opentech.usher.common.netty.DecentralizedConsumer;
import team.opentech.usher.common.netty.DecentralizedServer;
import team.opentech.usher.common.netty.DecentralizedServerImpl;
import team.opentech.usher.common.netty.DecentralizedUdpConsumerImpl;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.util.IdUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月06日 09时29分
 */
public class DecentralizedStarter {

    private final Object lock = new Object();

    private Integer port;

    private String clusterTypeCode;

    private Redisable redisable;

    private DecentralizedManager manager;

    private DecentralizedConsumer consumer;

    public DecentralizedStarter(Integer port, String clusterTypeCode, Redisable redisable, DecentralizedManager manager) {
        this.port = port;
        this.clusterTypeCode = clusterTypeCode;
        this.redisable = redisable;
        this.manager = manager;
    }

    /**
     * 启动server
     *
     * @throws InterruptedException
     */
    public void startServer() throws InterruptedException {
        DecentralizedServer decentralizedServer = new DecentralizedServerImpl(port, clusterTypeCode, redisable, manager);
        decentralizedServer.start();
    }

    public void sendUdp(byte[] body) throws InterruptedException {
        consumer.send(body);
    }

    /**
     * 创建一个udp consumer
     *
     * @return
     */
    private DecentralizedConsumer makeOrGetUdpConsumer(String clusterTypeCode, IdUtil idUtil) {
        if (consumer != null) {
            return consumer;
        }
        synchronized (lock) {
            consumer = new DecentralizedUdpConsumerImpl(clusterTypeCode, idUtil);
            return consumer;
        }
    }
}
