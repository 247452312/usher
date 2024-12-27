package top.uhyils.usher.elegant;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import top.uhyils.usher.elegant.controller.ElegantControllerFilter;
import top.uhyils.usher.elegant.controller.ElegantControllerFilterFinder;
import top.uhyils.usher.elegant.rpc.ElegantRpcFilterFinder;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月02日 18时44分
 */
@Configuration
@DependsOn("providerCluster")
public class ElegantServiceConfig {


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

    @Bean
    @DependsOn("providerCluster")
    public ElegantControllerFilterFinder elegantControllerFilterFinder() {
        return new ElegantControllerFilterFinder();
    }

    @Bean
    @DependsOn("providerCluster")
    public ElegantRpcFilterFinder elegantRpcFilterFinder() {
        return new ElegantRpcFilterFinder();
    }


}
