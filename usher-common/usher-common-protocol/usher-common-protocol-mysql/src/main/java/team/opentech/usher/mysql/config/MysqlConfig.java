package team.opentech.usher.mysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team.opentech.usher.mysql.content.MysqlGlobalVariables;
import team.opentech.usher.mysql.content.info.GlobalCustomExtendInfo;
import team.opentech.usher.mysql.content.info.GlobalCustomExtendInfoBlackExample;
import team.opentech.usher.mysql.content.info.SessionCustomExtendInfo;
import team.opentech.usher.mysql.content.info.SessionCustomExtendInfoBlackExample;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月22日 09时01分
 */
@Configuration
public class MysqlConfig {


    @Bean
    public MysqlGlobalVariables mysqlGlobalVariables(GlobalCustomExtendInfo globalCustomExtendInfo, SessionCustomExtendInfo sessionCustomExtendInfo) {
        MysqlGlobalVariables mysqlGlobalVariables = new MysqlGlobalVariables();
        mysqlGlobalVariables.setCustomExtendInfo(globalCustomExtendInfo);
        mysqlGlobalVariables.getSession().setCustomExtendInfo(sessionCustomExtendInfo);
        return mysqlGlobalVariables;
    }

    @Bean
    public GlobalCustomExtendInfo globalCustomExtendBlackInfo() {
        return new GlobalCustomExtendInfoBlackExample();
    }

    @Bean
    public SessionCustomExtendInfo sessionCustomExtendBlackInfo() {
        return new SessionCustomExtendInfoBlackExample();
    }
}
