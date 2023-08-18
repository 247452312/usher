package team.opentech.usher.protocol.mysql.netty.impl;


import team.opentech.usher.BaseTest;
import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.MysqlHandlerStatusEnum;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.plan.MysqlPlan;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月17日 14时25分
 */
public class MysqlNettyServerImplTest extends BaseTest {

    @Test
    void analysisSql() {
        MysqlTcpInfo value = new MysqlTcpInfo();
        value.setStatus(MysqlHandlerStatusEnum.PASSED);
        value.setDatabase("ushergateway");
        MysqlContent.MYSQL_TCP_INFO.set(value);

        String sql = "select a.*,b.*,b.id from sys_user a left join sys_role b on a.role_id = b.id where a.id = 12 and b.name in (select max(name) from sys_role where delete_flag = 0) and (b.name,b.id) in (select name,id from sys_user order by id desc limit 2)  ";
        List<MysqlPlan> mysqlPlans = MysqlUtil.analysisSqlToPlan(sql, null);
        int i = 1;
    }

    @Test
    void analysisSql2() {
        MysqlTcpInfo value = new MysqlTcpInfo();
        value.setStatus(MysqlHandlerStatusEnum.PASSED);
        value.setDatabase("ushergateway");
        MysqlContent.MYSQL_TCP_INFO.set(value);
        String sql = "select schema_name,default_character_set_name,default_collation_name from information_schema.schemata";
        List<MysqlPlan> mysqlPlans = MysqlUtil.analysisSqlToPlan(sql, null);
        int i = 1;
    }


}
