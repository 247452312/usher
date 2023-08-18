package team.opentech.usher.mysql.config;

import team.opentech.usher.mysql.content.MysqlGlobalVariables;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月22日 09时01分
 */
@Configuration
public class MysqlConfig {


    @Bean
    public MysqlGlobalVariables mysqlGlobalVariables() {
        return new MysqlGlobalVariables();
    }
}
