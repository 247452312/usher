package team.opentech.usher.redis.config;

import team.opentech.usher.redis.druid.filter.TableSqlFilter;
import team.opentech.usher.redis.hotspot.HotSpotRedisPool;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年05月11日 08时55分
 */
@Configuration
@Import({
            HotSpotRedisPool.class,
            TableSqlFilter.class
        })
public class HotSpotConfiguration {

}
