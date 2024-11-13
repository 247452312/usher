package team.opentech.usher.pojo.entity;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import team.opentech.usher.enums.DbTypeEnum;
import team.opentech.usher.pojo.DTO.request.DbInformationDTO;
import team.opentech.usher.util.KproUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月08日 16时16分
 */
class DbInformationTest {

    @Test
    void fillTableInfos() {
        DbInformationDTO dto = new DbInformationDTO();
        dto.setTables(Arrays.asList("sys_ai_device", "sys_ai_device_instruction"));
        dto.setDbName("usher_ai_control");
        dto.setUrl("jdbc:mysql://usher:3306/usher_ai_control?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&autoconnect=true&failOverReadOnly=false");
        dto.setType(DbTypeEnum.MYSQL.getTypeCode());
        dto.setUserName("root");
        dto.setPassword("1234");
        dto.setProjectName("usher");
        dto.setPackagePrefix("team.opentech.usher");
        dto.setPort(3300);
        dto.setAuthor("uhyils <247452312@qq.com>");

        DbInformation db = new DbInformation(dto);

        db.connect();
        db.fillTableInfos();
        Map<String, String> result = db.result();

        KproUtil.saveToLocal("D:\\usher\\生成文件\\hcos", result);

    }
}
