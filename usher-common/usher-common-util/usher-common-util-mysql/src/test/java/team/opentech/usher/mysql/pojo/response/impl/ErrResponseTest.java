package team.opentech.usher.mysql.pojo.response.impl;


import team.opentech.usher.mysql.content.MysqlContent;
import team.opentech.usher.mysql.enums.MysqlHandlerStatusEnum;
import team.opentech.usher.mysql.handler.MysqlTcpInfo;
import team.opentech.usher.mysql.util.MysqlUtil;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月06日 14时49分
 */
public class ErrResponseTest {

    @Test
    public void errResponseToByteTest() {
        MysqlTcpInfo value = new MysqlTcpInfo();
        value.setStatus(MysqlHandlerStatusEnum.PASSED);
        MysqlContent.MYSQL_TCP_INFO.set(value);
        byte[] bytes = MysqlUtil.mergeListBytes(ErrResponse.build("错误信息").toByte());
        String dump = MysqlUtil.dump(bytes);
        System.out.println(dump);


    }
}
