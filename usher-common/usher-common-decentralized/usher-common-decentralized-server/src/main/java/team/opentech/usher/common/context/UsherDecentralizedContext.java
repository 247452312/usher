package team.opentech.usher.common.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import team.opentech.usher.annotation.Nullable;
import team.opentech.usher.common.netty.DecentralizedConsumer;
import team.opentech.usher.common.netty.DecentralizedServer;
import team.opentech.usher.common.netty.DecentralizedTcpConsumerImpl;
import team.opentech.usher.common.netty.DecentralizedUdpConsumerImpl;
import team.opentech.usher.enums.DecentralizedClusterStatusEnum;
import team.opentech.usher.enums.DecentralizedNodeRoleEnum;
import team.opentech.usher.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.util.IdUtil;
import team.opentech.usher.util.IpUtil;
import team.opentech.usher.util.SpringUtil;

/**
 * 去中心化集群上下文
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月06日 10时19分
 */
public class UsherDecentralizedContext {

    /**
     * 唯一标示暂存
     */
    private static final String CACHE_UNIQUE_KEY = "decentralized:unique:%d";

    /**
     * 唯一标示暂存所需要的时间 10分钟
     */
    private static final Integer CACHE_UNIQUE_TIME = 60 * 10;

    private final Redisable redisable;

    /**
     * 服务端端口
     */
    private final Integer serverPort;

    /**
     * 本机对应集群唯一标示
     */
    private final String clusterTypeCode;

    private Object lock = new Object();

    /**
     * 当前节点的状态
     */
    private DecentralizedClusterStatusEnum status = DecentralizedClusterStatusEnum.INIT;

    /**
     * 本机ip
     */
    private volatile String host;

    /**
     * 当前用户角色
     */
    private DecentralizedNodeRoleEnum role;

    /**
     * 刚上线等待链接的node
     */
    private volatile List<DecentralizedNode> waitNode = new ArrayList<>();

    /**
     * 最后一个上线的机器
     */
    private volatile long lastWaitNode;

    /**
     * udpConsumer为单例
     */
    private volatile DecentralizedConsumer udpSingleConsumer;

    /**
     * tcpConsumer key-> ip:port
     */
    private volatile Map<String, DecentralizedConsumer> tcpConsumers;

    /**
     * 服务端
     */
    private volatile DecentralizedServer decentralizedServer;

    /**
     * 提议选举的节点ip
     */
    private volatile String proposeLeaderNodeHost;

    /**
     * 提议选举的节点端口
     */
    private volatile Integer proposeLeaderNodePort;


    /**
     * leader_ip
     */
    private volatile String leaderNodeHost;

    /**
     * leader_port
     */
    private volatile Integer leaderNodePort;

    private List<DecentralizedNode> ackNodes = new ArrayList<>();

    public UsherDecentralizedContext(Redisable redisable, Integer serverPort, String clusterTypeCode) {
        this.redisable = redisable;
        this.serverPort = serverPort;
        this.clusterTypeCode = clusterTypeCode;
        this.role = DecentralizedNodeRoleEnum.NO_ELECTIONS;
        this.tcpConsumers = new HashMap<>();
    }

    public static UsherDecentralizedContext getInstance() {
        return SpringUtil.getBean(UsherDecentralizedContext.class);
    }

    public String getLeaderNodeHost() {
        return leaderNodeHost;
    }

    public void setLeaderNodeHost(String leaderNodeHost) {
        this.leaderNodeHost = leaderNodeHost;
    }

    public Integer getLeaderNodePort() {
        return leaderNodePort;
    }

    public void setLeaderNodePort(Integer leaderNodePort) {
        this.leaderNodePort = leaderNodePort;
    }

    public String getProposeLeaderNodeHost() {
        return proposeLeaderNodeHost;
    }

    public void setProposeLeaderNodeHost(String proposeLeaderNodeHost) {
        this.proposeLeaderNodeHost = proposeLeaderNodeHost;
    }

    public Integer getProposeLeaderNodePort() {
        return proposeLeaderNodePort;
    }

    public void setProposeLeaderNodePort(Integer proposeLeaderNodePort) {
        this.proposeLeaderNodePort = proposeLeaderNodePort;
    }

    public DecentralizedServer getServer() {
        return decentralizedServer;
    }

    public void setServer(DecentralizedServer decentralizedServer) {
        this.decentralizedServer = decentralizedServer;
    }

    /**
     * 创建一个udp consumer
     *
     * @return
     */
    public DecentralizedConsumer makeOrGetUdpConsumer() {
        if (udpSingleConsumer != null) {
            return udpSingleConsumer;
        }
        synchronized (lock) {
            IdUtil idUtil = SpringUtil.getBean(IdUtil.class);
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
            IdUtil idUtil = SpringUtil.getBean(IdUtil.class);
            if (tcpConsumers.containsKey(key)) {
                return tcpConsumers.get(key);
            }
            DecentralizedTcpConsumerImpl decentralizedTcpConsumer = new DecentralizedTcpConsumerImpl(host, port, idUtil);
            tcpConsumers.put(key, decentralizedTcpConsumer);
            return decentralizedTcpConsumer;
        }
    }

    /**
     * 当前用户角色
     *
     * @return
     */
    public DecentralizedNodeRoleEnum role() {
        return role;
    }

    @Nullable
    public Long findUnique(DecentralizedRequestTypeEnum requestTypeEnum) {
        String cacheUniqueKey = getCacheUniqueKey(requestTypeEnum);
        return Long.valueOf(redisable.get(cacheUniqueKey));
    }

    /**
     * 暂存唯一标示
     *
     * @param requestTypeEnum
     * @param unique
     */
    public void putUnique(DecentralizedRequestTypeEnum requestTypeEnum, Long unique) {
        String cacheUniqueKey = getCacheUniqueKey(requestTypeEnum);
        redisable.set(cacheUniqueKey, String.valueOf(unique));
        redisable.expire(cacheUniqueKey, CACHE_UNIQUE_TIME);
    }

    public Redisable cache() {
        return redisable;
    }

    /**
     * 获取状态
     *
     * @return
     */
    public DecentralizedClusterStatusEnum status() {
        return status;
    }

    public Integer serverPort() {
        return serverPort;
    }

    public String clusterTypeCode() {
        return clusterTypeCode;
    }

    /**
     * 本机ip
     *
     * @return
     */
    public String host() {
        if (host != null) {
            return host;
        }
        return host = IpUtil.getIp();
    }

    /**
     * 刚上线的节点
     *
     * @return
     */
    public List<DecentralizedNode> waitNode() {
        return waitNode;
    }

    public long lastWaitNodeTime() {
        return lastWaitNode;
    }

    public long flushLastWaitNodeTime() {
        this.lastWaitNode = System.currentTimeMillis();
        return this.lastWaitNode;
    }

    /**
     * 获取udpConsumer
     *
     * @return
     */
    public DecentralizedConsumer udpConsumer() {
        return udpSingleConsumer;
    }

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
     * 添加ack节点
     *
     * @param ip   响应节点ip
     * @param port 响应节点端口
     *
     * @return ack节点数量
     */
    public Integer putAckNode(String ip, Integer port) {
        DecentralizedNode decentralizedNode = new DecentralizedNode(ip, port);
        if (!ackNodes.contains(decentralizedNode)) {
            ackNodes.add(decentralizedNode);
        }
        return ackNodes.size();
    }

    private String makeKey(String host, Integer port) {
        return String.format("Decentralized_%s:%d", host, port);
    }

    private String getCacheUniqueKey(DecentralizedRequestTypeEnum requestTypeEnum) {
        return String.format(CACHE_UNIQUE_KEY, requestTypeEnum.getCode());
    }

    /**
     * 机器节点
     */
    public static class DecentralizedNode {

        private String ip;

        private Integer port;

        public DecentralizedNode() {
        }

        public DecentralizedNode(String ip, Integer port) {
            this.ip = ip;
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            DecentralizedNode node = (DecentralizedNode) o;
            return Objects.equals(ip, node.ip) && Objects.equals(port, node.port);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ip, port);
        }
    }
}
