package team.opentech.usher.pojo.entity;

import team.opentech.usher.enums.DbTypeEnum;
import team.opentech.usher.pojo.DTO.request.DbInformationDTO;
import team.opentech.usher.util.KproUtil;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月08日 16时16分
 */
class DbInformationTest {

    @Test
    void fillTableInfos() {
        DbInformationDTO dto = new DbInformationDTO();
        dto.setTables(Arrays.asList("user"));
        dto.setDbName("test");
        dto.setUrl("jdbc:mysql://localhost:3300/my?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&autoconnect=true&failOverReadOnly=false&useSSL=false");
        dto.setType(DbTypeEnum.MYSQL.getTypeCode());
        dto.setUserName("root");
        dto.setPassword("123456");
        dto.setProjectName("usher");
        dto.setPort(3300);
        dto.setAuthor("uhyils<247452312@qq.com>");

        DbInformation db = new DbInformation(dto);

        db.connect();
        db.fillTableInfos();
        Map<String, String> result = db.result();

        KproUtil.saveToLocal("D:\\usher\\生成文件\\hcos", result);

    }
}
