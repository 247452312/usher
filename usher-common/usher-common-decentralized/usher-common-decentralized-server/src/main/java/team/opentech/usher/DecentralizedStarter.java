package team.opentech.usher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import team.opentech.usher.common.netty.DecentralizedConsumer;
import team.opentech.usher.common.netty.DecentralizedServer;
import team.opentech.usher.common.netty.DecentralizedServerImpl;
import team.opentech.usher.common.netty.DecentralizedTcpConsumerImpl;
import team.opentech.usher.common.netty.DecentralizedUdpConsumer;
import team.opentech.usher.common.netty.DecentralizedUdpConsumerImpl;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.util.IdUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月06日 09时29分
 */
public class DecentralizedStarter {

    private final Object lock = new Object();

    /**
     * 各个业务处理工具
     */
    private DecentralizedManager manager;

    /**
     * udpConsumer为单例
     */
    private volatile DecentralizedUdpConsumer udpSingleConsumer;

    /**
     * tcpConsumer key-> ip:port
     */
    private volatile Map<String, DecentralizedConsumer> tcpConsumers;

    /**
     * 服务端
     */
    private volatile DecentralizedServer decentralizedServer;

    /**
     * id生成器
     */
    private IdUtil idUtil;


    public DecentralizedStarter(DecentralizedManager manager, IdUtil idUtil) {
        this.idUtil = idUtil;
        this.manager = manager;
        this.tcpConsumers = new HashMap<>();
    }

    /**
     * 启动server
     *
     * @throws InterruptedException
     */
    public void startServer() throws InterruptedException {
        if (decentralizedServer == null) {
            decentralizedServer = new DecentralizedServerImpl(manager);
        }
        decentralizedServer.start();
    }

    public void sendUdp(byte[] body, DecentralizedRequestTypeEnum decentralizedRequestType) throws InterruptedException {
        udpSingleConsumer.send(body, decentralizedRequestType);
    }

    /**
     * 是否在线
     *
     * @return
     */
    public Boolean isOnline() {
        return decentralizedServer.isOnline();
    }

    /**
     * 强制关闭
     */
    public void close() {
        if (decentralizedServer != null) {
            decentralizedServer.close();
        }
        if (udpSingleConsumer != null) {
            udpSingleConsumer.close();
        }

        for (Entry<String, DecentralizedConsumer> consumerEntry : tcpConsumers.entrySet()) {
            consumerEntry.getValue().close();
        }
    }

    public void shutdown() throws InterruptedException {
        if (decentralizedServer != null) {
            decentralizedServer.shutdown();
        }
        if (udpSingleConsumer != null) {
            udpSingleConsumer.shutdown();
        }

        for (Entry<String, DecentralizedConsumer> consumerEntry : tcpConsumers.entrySet()) {
            consumerEntry.getValue().shutdown();
        }
    }

    /**
     * 创建一个udp consumer
     *
     * @return
     */
    public DecentralizedUdpConsumer makeOrGetUdpConsumer() {
        if (udpSingleConsumer != null) {
            return udpSingleConsumer;
        }
        synchronized (lock) {
            udpSingleConsumer = new DecentralizedUdpConsumerImpl(decentralizedServer, idUtil);
            return udpSingleConsumer;
        }
    }

    /**
     * 创建一个tcp consumer
     *
     * @return
     */
    public DecentralizedConsumer makeOrGetTcpConsumer(String host, Integer port) {
        String key = makeKey(host, port);
        if (tcpConsumers.containsKey(key)) {
            return tcpConsumers.get(key);
        }
        synchronized (key) {
            if (tcpConsumers.containsKey(key)) {
                return tcpConsumers.get(key);
            }
            DecentralizedTcpConsumerImpl decentralizedTcpConsumer = new DecentralizedTcpConsumerImpl(host, port, idUtil);
            tcpConsumers.put(key, decentralizedTcpConsumer);
            return decentralizedTcpConsumer;
        }
    }

    private String makeKey(String host, Integer port) {
        return String.format("Decentralized_%s:%d", host, port);
    }
}
