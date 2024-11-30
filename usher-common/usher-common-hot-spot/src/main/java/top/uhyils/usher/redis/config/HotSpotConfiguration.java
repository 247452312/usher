package top.uhyils.usher.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import top.uhyils.usher.redis.druid.filter.TableSqlFilter;
import top.uhyils.usher.redis.hotspot.HotSpotRedisPool;


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
