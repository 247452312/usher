package team.opentech.usher.pojo.entity;

import java.util.Arrays;
import java.util.List;
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
        List<String> tables = Arrays.asList("hcos_address");

        dto.setTables(tables);
        String dbName = "test";
        String username = "root";
        String password = "123456";

        dto.setDbName(dbName);
        dto.setUrl("jdbc:mysql://mac:3306/" + dbName + "?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&allowMultiQueries=true&autoReconnect=true&failOverReadOnly=false&connectTimeout=3000&socketTimeout=6000&zeroDateTimeBehavior=convertToNull&useSSL=false");
        dto.setType(DbTypeEnum.MYSQL.getTypeCode());
        dto.setUserName(username);
        dto.setPassword(password);
        dto.setProjectName(dbName);
        dto.setPort(3300);
        dto.setAuthor("uhyils <247452312@qq.com>");
        dto.setPackagePrefix("com.c2f.hcos.coordination.core");

        DbInformation db = new DbInformation(dto);

        db.connect();
        db.fillTableInfos();
        Map<String, String> result = db.result();

        KproUtil.saveToLocal("D:\\usher\\生成文件\\hcos", result);

    }
}
