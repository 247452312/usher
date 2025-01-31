package team.opentech.usher.pojo.entity.sys;

import java.lang.reflect.Field;
import java.util.Date;
import org.junit.jupiter.api.Test;
import team.opentech.usher.mysql.enums.TableTypeEnum;
import team.opentech.usher.mysql.pojo.DTO.ISchemaPrivilegesInfo;
import team.opentech.usher.util.StringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月08日 14时02分
 */
class ITablesTableTest {

    @Test
    public void testTableInfo() {
        Field[] fields = ISchemaPrivilegesInfo.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String name = field.getName();
            Class<?> type = field.getType();
            String upperCase = StringUtil.toUnderline(name).toUpperCase();
            String typeStr = null;
            if (type.isAssignableFrom(String.class)) {
                typeStr = "FIELD_TYPE_VARCHAR";
            } else if (Number.class.isAssignableFrom(type)) {
                typeStr = "FIELD_TYPE_INT24";
            } else if (type.isAssignableFrom(Date.class)) {
                typeStr = "FIELD_TYPE_TIMESTAMP";
            } else if (type.isAssignableFrom(TableTypeEnum.class)) {
                typeStr = "FIELD_TYPE_VARCHAR";
            }
            if (typeStr == null) {
                throw new RuntimeException("不对," + name + ",类型是:" + type.getName());
            }
            System.out.println("fieldInfos.add(new FieldInfo(\"information_schema\", \"schema_privileges\", \"schema_privileges\", \"" + upperCase + "\", \"" + upperCase + "\", 0, 1, FieldTypeEnum." + typeStr + ", (short) 0, (byte) 0));");
        }
    }

}
