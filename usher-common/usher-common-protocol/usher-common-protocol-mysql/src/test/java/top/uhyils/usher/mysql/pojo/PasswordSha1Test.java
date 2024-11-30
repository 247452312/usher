package top.uhyils.usher.mysql.pojo;

import org.junit.jupiter.api.Test;
import top.uhyils.usher.mysql.util.MysqlUtil;
import top.uhyils.usher.util.LogUtil;

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
