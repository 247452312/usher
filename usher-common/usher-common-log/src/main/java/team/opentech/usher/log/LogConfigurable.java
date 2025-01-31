package team.opentech.usher.log;

import team.opentech.usher.log.filter.controller.ControllerLogFilter;
import team.opentech.usher.log.filter.db.DbLogFilter;
import team.opentech.usher.log.filter.task.TaskLogAop;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月25日 14时24分
 */
@Configuration
@Import({DbLogFilter.class, TaskLogAop.class})
public class LogConfigurable {

    @Bean
    public FilterRegistrationBean<ControllerLogFilter> logControllerFilter() {

        FilterRegistrationBean<ControllerLogFilter> filterBean = new FilterRegistrationBean<>();
        filterBean.setFilter(new ControllerLogFilter());
        filterBean.setName("controllerLogFilter");
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }
}
