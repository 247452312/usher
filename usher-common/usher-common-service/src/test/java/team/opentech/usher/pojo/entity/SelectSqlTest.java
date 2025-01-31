package team.opentech.usher.pojo.entity;

import team.opentech.usher.util.LogUtil;
import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月08日 07时38分
 */
public class SelectSqlTest {

    private String sqlStr;

    @BeforeEach
    public void setUp() throws Exception {
        sqlStr = "select a.id,\n"
                 + "               a.create_date,\n"
                 + "               a.create_user,\n"
                 + "               a.delete_flag,\n"
                 + "               a.remark,\n"
                 + "               a.update_date,\n"
                 + "               a.update_user,\n"
                 + "               a.name\n"
                 + "        from sys_dept a\n"
                 + "                 left join sys_role_dept srd on a.id = srd.dept_id\n"
                 + "        where srd.role_id = #{roleId}";
    }

    @AfterEach
    public void tearDown() throws Exception {

    }

    @Test
    public void fillDeleteFlag() {
        Sql sql = new Sql(sqlStr, Arrays.asList("sys_role_dept"));
        SelectSql transformation = (SelectSql) sql.transformation();
        transformation.fillDeleteFlag();
        LogUtil.info(transformation.sql());
    }
}
