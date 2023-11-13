package team.opentech.usher.common.context;

import team.opentech.usher.annotation.Nullable;
import team.opentech.usher.common.netty.enums.DecentralizedRequestTypeEnum;
import team.opentech.usher.enums.ClusterStatusEnum;
import team.opentech.usher.redis.Redisable;
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

    /**
     * 当前节点的状态
     */
    private ClusterStatusEnum status = ClusterStatusEnum.INIT;

    /**
     * 本机ip
     */
    private volatile String host;

    public UsherDecentralizedContext(Redisable redisable, Integer serverPort, String clusterTypeCode) {
        this.redisable = redisable;
        this.serverPort = serverPort;
        this.clusterTypeCode = clusterTypeCode;
    }

    public static UsherDecentralizedContext getInstance() {
        return SpringUtil.getBean(UsherDecentralizedContext.class);
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
    public ClusterStatusEnum status() {
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

    private String getCacheUniqueKey(DecentralizedRequestTypeEnum requestTypeEnum) {
        return String.format(CACHE_UNIQUE_KEY, requestTypeEnum.getCode());
    }
}
