package team.opentech.usher.plan.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLUseStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import team.opentech.usher.plan.MysqlPlan;
import team.opentech.usher.plan.PlanFactory;

/**
 * sql
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2023年08月29日 10时20分
 */
@Component
public class UseSqlParser implements SqlParser {

    @Resource
    private PlanFactory planFactory;

    @Override
    public boolean canParse(SQLStatement sql) {
        if (sql instanceof SQLUseStatement) {
            return true;
        }
        return false;
    }

    @Override
    public List<MysqlPlan> parse(SQLStatement sql, Map<String, String> headers) {
        SQLUseStatement sqlUseStatement = (SQLUseStatement) sql;
        String simpleName = sqlUseStatement.getDatabase().getSimpleName();
        return Arrays.asList(planFactory.buildUsePlan(simpleName, headers));
    }
}
