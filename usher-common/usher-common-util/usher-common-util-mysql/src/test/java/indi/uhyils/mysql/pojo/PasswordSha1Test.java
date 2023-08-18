package team.opentech.usher.mysql.pojo;

import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.LogUtil;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月23日 09时25分
 */
public class PasswordSha1Test {

    @Test
    public void passwordSha1Test() {
        String s = MysqlUtil.sha1("123456");
        LogUtil.info(s);
    }

}
