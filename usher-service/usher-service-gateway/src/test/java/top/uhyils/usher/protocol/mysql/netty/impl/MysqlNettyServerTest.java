package top.uhyils.usher.protocol.mysql.netty.impl;


import java.util.List;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.BaseTest;
import top.uhyils.usher.content.CallNodeContent;
import top.uhyils.usher.content.CallerUserInfo;
import top.uhyils.usher.mysql.content.MysqlContent;
import top.uhyils.usher.mysql.enums.MysqlHandlerStatusEnum;
import top.uhyils.usher.mysql.pojo.entity.MysqlTcpLink;
import top.uhyils.usher.plan.SqlPlan;
import top.uhyils.usher.util.PlanUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月17日 14时25分
 */
public class MysqlNettyServerTest extends BaseTest {

    @Test
    void analysisSql() {
        MysqlTcpLink value = MysqlTcpLink.build(null, null);
        CallerUserInfo value1 = new CallerUserInfo();
        value1.setDatabaseName("ushergateway");
        CallNodeContent.CALLER_INFO.set(value1);
        value.setStatus(MysqlHandlerStatusEnum.PASSED);
        MysqlContent.MYSQL_TCP_INFO.set(value);

        String sql = "select a.*,b.*,b.id from sys_user a left join sys_role b on a.role_id = b.id where a.id = 12 and b.name in (select max(name) from sys_role where delete_flag = 0) and (b.name,b.id) in (select name,id from sys_user order by id desc limit 2)  ";
        List<SqlPlan> mysqlPlans = PlanUtil.analysisSqlToPlan(sql, null);
        int i = 1;
    }

    @Test
    void analysisSql2() {
        MysqlTcpLink value = MysqlTcpLink.build(null, null);
        CallerUserInfo value1 = new CallerUserInfo();
        value1.setDatabaseName("ushergateway");
        value.setStatus(MysqlHandlerStatusEnum.PASSED);
        CallNodeContent.CALLER_INFO.set(value1);
        MysqlContent.MYSQL_TCP_INFO.set(value);
        String sql = "select schema_name,default_character_set_name,default_collation_name from information_schema.schemata";
        List<SqlPlan> mysqlPlans = PlanUtil.analysisSqlToPlan(sql, null);
        int i = 1;
    }


}
