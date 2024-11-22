package team.opentech.usher.elegant;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import team.opentech.usher.elegant.assembly.controller.ElegantControllerFilter;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月02日 18时44分
 */
@Configuration
@Import({ElegantCoreProcessor.class})
@DependsOn("providerCluster")
public class ElegantConfig {


    @Bean
    public ElegantControllerFilter elegantControllerFilter() {
        return new ElegantControllerFilter();
    }

    @Bean
    public FilterRegistrationBean<ElegantControllerFilter> elegantControllerFilterFilterRegistrationBean(ElegantControllerFilter filter) {
        FilterRegistrationBean<ElegantControllerFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(filter);
        filterBean.setName("controllerElegantFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }


}
