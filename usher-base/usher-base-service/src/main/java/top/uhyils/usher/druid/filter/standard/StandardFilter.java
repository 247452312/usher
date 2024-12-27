package top.uhyils.usher.druid.filter.standard;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.PreparedStatementProxy;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.entity.InsertSql;
import top.uhyils.usher.pojo.entity.SelectSql;
import top.uhyils.usher.pojo.entity.Sql;
import top.uhyils.usher.pojo.entity.UpdateSql;
import top.uhyils.usher.util.LogUtil;
import top.uhyils.usher.util.SpringUtil;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月07日 10时42分
 */
@Component
public class StandardFilter extends FilterEventAdapter {


    private List<String> ignoreTableNames;

    public StandardFilter() {
        this.ignoreTableNames = Arrays.asList(SpringUtil.getProperty("spring.datasource.ignore.tables", "").split(","));
    }

    @Override
    public PreparedStatementProxy connection_prepareStatement(FilterChain chain, ConnectionProxy connection, String sqlStr) throws SQLException {
        Sql sql = new Sql(sqlStr, ignoreTableNames);
        sql.parse();
        Sql.SqlType type = sql.type();
        Long userId = getUserId();
        switch (type) {
            case SELECT:
                SelectSql transformation = (SelectSql) sql.transformation();
                transformation.fillDeleteFlag();
                return super.connection_prepareStatement(chain, connection, transformation.sql());
            case UPDATE:
                UpdateSql updateSql = (UpdateSql) sql.transformation();
                updateSql.fillDeleteFlag();
                updateSql.addLongSet("update_date", System.currentTimeMillis());
                updateSql.addLongSet("update_user", userId);
                return super.connection_prepareStatement(chain, connection, updateSql.sql());
            case INSERT:
                InsertSql insertSql = (InsertSql) sql.transformation();
                insertSql.fillDeleteFlag();
                long time = System.currentTimeMillis();
                insertSql.addLongItem("update_date", time);
                insertSql.addLongItem("update_user", userId);
                insertSql.addLongItem("create_date", time);
                insertSql.addLongItem("create_user", userId);
                return super.connection_prepareStatement(chain, connection, insertSql.sql());
            case DELETE:
            default:
                LogUtil.info(sqlStr);
                return super.connection_prepareStatement(chain, connection, sqlStr);
        }
    }

    private Long getUserId() {
        UserDTO userDTO = LoginInfoHelper.doGet();
        Long userId;
        if (userDTO != null) {
            userId = userDTO.getId();
        } else {
            userId = 0L;
        }
        return userId;
    }
}
