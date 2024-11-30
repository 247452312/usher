package top.uhyils.usher.mysql.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import top.uhyils.usher.mysql.pojo.pool.SqlTableSourceBinaryTreePool;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月31日 17时07分
 */
public class MysqlPlanConfiguration {


    @Value("${mysql.plan.table.pool.size:1000}")
    private Integer size;

    @Value("${mysql.allow-fault:true}")
    private Boolean allowFault;

    @Bean
    public SqlTableSourceBinaryTreePool createSqlTableSourceBinaryTreePool() {
        return new SqlTableSourceBinaryTreePool(size);
    }


    @Bean
    public MysqlPlanConfig config() {
        MysqlPlanConfig mysqlPlanConfig = new MysqlPlanConfig();
        mysqlPlanConfig.setAllowFault(allowFault);
        return mysqlPlanConfig;
    }


}
