package team.opentech.usher.config;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.opentech.usher.common.context.UsherDecentralizedContext;
import team.opentech.usher.redis.Redisable;

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


    @Bean
    public UsherDecentralizedContext usherDecentralizedContext() {
        return new UsherDecentralizedContext(redisable, port, clusterTypeCode);
    }

}
