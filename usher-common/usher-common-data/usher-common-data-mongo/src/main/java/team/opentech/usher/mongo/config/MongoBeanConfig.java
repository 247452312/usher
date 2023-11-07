package team.opentech.usher.mongo.config;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年07月01日 14时31分
 */
@Configuration
public class MongoBeanConfig {

    @Resource
    private MongoConfig mongoConfig;

    @Bean
    public MongoDbFactory getMongoDbFactory() {
        return MongoDbFactory.getInstance(mongoConfig);
    }
}
