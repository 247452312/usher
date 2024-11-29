package team.opentech.usher.config;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import team.opentech.usher.config.mysql.info.GlobalCustomExtendInfoImpl;
import team.opentech.usher.config.mysql.info.SessionCustomExtendInfoImpl;
import team.opentech.usher.mysql.config.MysqlConfig;
import team.opentech.usher.mysql.content.info.GlobalCustomExtendInfo;
import team.opentech.usher.mysql.content.info.SessionCustomExtendInfo;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 17时48分
 */
@Configuration
@AutoConfigureBefore(MysqlConfig.class)
public class MysqlConfiguration {


    @Bean
    @Primary
    public GlobalCustomExtendInfo globalCustomExtendInfo() {
        return new GlobalCustomExtendInfoImpl();
    }

    @Bean
    @Primary
    public SessionCustomExtendInfo sessionCustomExtendInfo() {
        return new SessionCustomExtendInfoImpl();
    }
}
