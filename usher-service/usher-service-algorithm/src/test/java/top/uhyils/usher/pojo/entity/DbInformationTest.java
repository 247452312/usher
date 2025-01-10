package top.uhyils.usher.pojo.entity;

import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.Test;
import top.uhyils.usher.enums.DbTypeEnum;
import top.uhyils.usher.pojo.DTO.request.DbInformationDTO;
import top.uhyils.usher.util.KproUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年02月08日 16时16分
 */
class DbInformationTest {

    @Test
    void fillTableInfos() {
        DbInformationDTO dto = new DbInformationDTO();
        dto.setTables(Arrays.asList("sys_net_node_info", "sys_net_node_info_detail"));
        dto.setDbName("usher_gateway");
        dto.setUrl("jdbc:mysql://mysql.uhyils.top/usher_gateway?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&autoconnect=true&failOverReadOnly=false");
        dto.setType(DbTypeEnum.MYSQL.getTypeCode());
        dto.setUserName("root");
        dto.setPassword("Shang1996.");
        dto.setProjectName("usher");
        dto.setPackagePrefix("top.uhyils.usher");
        dto.setPort(3300);
        dto.setAuthor("uhyils <247452312@qq.com>");

        DbInformation db = new DbInformation(dto);

        db.connect();
        db.fillTableInfos();
        Map<String, String> result = db.result();

        KproUtil.saveToLocal("D:\\usher\\生成文件\\hcos", result);

    }
}
