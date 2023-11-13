package team.opentech.usher.config;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import team.opentech.usher.DecentralizedStarter;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.core.DecentralizedManager;
import team.opentech.usher.redis.Redisable;
import team.opentech.usher.util.IdUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年11月03日 16时08分
 */
@Configuration
public class DecentralizedConfiguration {

    @Value("${decentralized.port:8080}")
    private Integer port;

    @Value("${decentralized.clusterTypeCode:default}")
    private String clusterTypeCode;

    @Resource
    private Redisable redisable;

    @Resource
    private DecentralizedManager manager;

    @Resource
    private IdUtil idUtil;


    /**
     * 去中心化集群启动器构建
     * 注: 构建之后并不会立即启动,需要等待优雅上下线部分成功后再进行启动
     */
    @Bean
    @DependsOn("usherDecentralizedContext")
    public DecentralizedStarter decentralizedStarter() {
        return new DecentralizedStarter(manager, idUtil);
    }

    @Bean
    public UsherDecentralizedContext usherDecentralizedContext() {
        return new UsherDecentralizedContext(redisable, port, clusterTypeCode);
    }

}
