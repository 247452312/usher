package top.uhyils.usher.elegant;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月25日 16时31分
 */
@Configuration
public class ElegantConfig {


    @Bean
    public ElegantCoreProcessor makeElegantCoreProcessor() {
        return new ElegantCoreProcessor();
    }
}
