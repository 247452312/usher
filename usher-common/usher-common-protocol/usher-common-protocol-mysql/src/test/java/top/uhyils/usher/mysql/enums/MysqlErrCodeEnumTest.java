package top.uhyils.usher.mysql.enums;


import org.junit.jupiter.api.Test;
import top.uhyils.usher.mysql.util.MysqlUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月06日 14时55分
 */
public class MysqlErrCodeEnumTest {

    @Test
    public void getByteCode() {
        byte[] byteCode = MysqlErrCodeEnum.EE_STAT.getByteCode();
        String dump = MysqlUtil.dump(byteCode);
        System.out.println(dump);

    }
}
