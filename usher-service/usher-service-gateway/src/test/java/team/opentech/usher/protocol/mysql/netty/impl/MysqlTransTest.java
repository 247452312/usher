package team.opentech.usher.protocol.mysql.netty.impl;

import team.opentech.usher.mysql.enums.FieldTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.FieldInfo;
import team.opentech.usher.mysql.pojo.response.impl.ResultSetResponse;
import team.opentech.usher.mysql.util.MysqlUtil;
import team.opentech.usher.protocol.mysql.netty.impl.other.MysqlNettyTest;
import team.opentech.usher.util.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年08月23日 14时37分
 */
public class MysqlTransTest {


    @Test
    void trans() throws InterruptedException {
        MysqlNettyTest mysqlNettyTest = new MysqlNettyTest();
        mysqlNettyTest.init();
        Thread.sleep(100000000L);
    }


    @Test
    void responseByteTest() {
        ArrayList<FieldInfo> fields = new ArrayList<>();
        short ss = 0;
        fields.add(new FieldInfo("testDB", "sys_table", "sys_table", "id", "id", 100, 1, FieldTypeEnum.FIELD_TYPE_LONG, ss, (byte) 0));
        fields.add(new FieldInfo("testDB", "sys_table", "sys_table", "name", "name", 100, 2, FieldTypeEnum.FIELD_TYPE_STRING, ss, (byte) 0));
        ArrayList<Map<String, Object>> jsonInfo = new ArrayList<>();
        HashMap<String, Object> firstLine = new HashMap<>();
        firstLine.put("id", 1L);
        firstLine.put("name", "name1");
        jsonInfo.add(firstLine);
        ResultSetResponse e = new ResultSetResponse(fields, jsonInfo);
        List<byte[]> bytes = e.toByte();
        for (byte[] aByte : bytes) {
            LogUtil.info("\n" + MysqlUtil.dump(aByte));
        }
    }

}
