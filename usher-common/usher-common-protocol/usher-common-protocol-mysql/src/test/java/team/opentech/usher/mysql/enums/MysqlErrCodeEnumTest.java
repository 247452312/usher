package team.opentech.usher.mysql.enums;


import org.junit.jupiter.api.Test;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.util.ByteUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月06日 14时55分
 */
public class MysqlErrCodeEnumTest {

    @Test
    public void getByteCode() {
        byte[] byteCode = MysqlErrCodeEnum.EE_STAT.getByteCode();
        String dump = ByteUtil.dump(byteCode);
        System.out.println(dump);

    }
}
