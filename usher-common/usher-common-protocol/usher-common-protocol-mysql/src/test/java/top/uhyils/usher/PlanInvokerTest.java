package top.uhyils.usher;

import org.junit.jupiter.api.Test;
import top.uhyils.usher.mysql.util.PlanUtil;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年03月30日 09时20分
 */
class PlanInvokerTest extends BaseTest {

    @Test
    void analysisSql() {
        String sql = "select a.*,b.* from sys_user a left join sys_role b on a.role_id = b.id where a.id = 12 ";
        Asserts.assertNoException(() -> PlanUtil.analysisSqlToPlan(sql, null));
    }

}

