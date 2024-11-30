package top.uhyils.usher.mysql.plan.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLAssignItem;
import com.alibaba.druid.sql.ast.statement.SQLSetStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSetTransactionStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import top.uhyils.usher.mysql.plan.MysqlPlan;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月29日 08时54分
 */
@Component
public class SetSqlParser implements SqlParser {

    @Override
    public boolean canParse(SQLStatement sql) {
        // 检查sql是不是set语句
        if (sql instanceof SQLSetStatement || sql instanceof MySqlSetTransactionStatement) {
            return true;
        }
        return false;
    }

    @Override
    public List<MysqlPlan> parse(SQLStatement sql, Map<String, String> headers) {

        // todo 暂时忽略set语句
        if (sql instanceof SQLSetStatement) {
            // 解析set语句
            SQLSetStatement setSql = (SQLSetStatement) sql;
            List<SQLAssignItem> items = setSql.getItems();
            for (SQLAssignItem sqlAssignItem : items) {
                // 要被改的变量名称
                String target = sqlAssignItem.getTarget().toString();
                // 改成什么
                String value = sqlAssignItem.getValue().toString();
            }
            return new ArrayList<>();
        } else if (sql instanceof MySqlSetTransactionStatement) {
            MySqlSetTransactionStatement setSql = (MySqlSetTransactionStatement) sql;
            return new ArrayList<>();
        }
        return new ArrayList<>();

    }
}
