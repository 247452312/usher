package top.uhyils.usher.mysql.util;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.mysql.plan.MysqlPlan;
import top.uhyils.usher.mysql.plan.parser.SqlParser;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月30日 17时41分
 */
public final class PlanUtil {

    private PlanUtil() {
        throw new RuntimeException("PlanUtil不能实例化");
    }


    /**
     * 解析mysql语句
     *
     * @param sql mysql语句
     *
     * @return
     */
    public static List<MysqlPlan> analysisSqlToPlan(String sql) {
        return analysisSqlToPlan(sql, new HashMap<>());
    }

    /**
     * 解析mysql语句
     *
     * @param sql mysql语句
     *
     * @return
     */
    public static List<MysqlPlan> analysisSqlToPlan(String sql, Map<String, String> headers) {
        SQLStatement sqlStatement = new MySqlStatementParser(sql).parseStatement();
        List<SqlParser> beans = SpringUtil.getBeans(SqlParser.class);
        for (SqlParser bean : beans) {
            if (bean.canParse(sqlStatement)) {
                return bean.parse(sqlStatement, headers);
            }
        }
        Asserts.throwException("解析执行计划失败:{}", sql);
        return null;
    }

}
