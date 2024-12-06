package top.uhyils.usher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.uhyils.usher.ql.QlPool;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 10时53分
 */
@Configuration
public class QlConfig {


    @Value("${ql.pool.coreSize:10}")
    private Integer poolCoreSize;

    @Bean
    public QlPool qlPool() {
        return new QlPool(poolCoreSize);
    }
}
