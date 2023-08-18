package team.opentech.usher.util.kpro;


import team.opentech.usher.annotation.NotNull;
import team.opentech.usher.enums.DbTypeEnum;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.kpro.convertor.mysql.MysqlTypeConvertor;

/**
 * TypeConvertor 工厂类
 *
 * @author 商轶龙 <247452312@qq.com>
 * @copyright Copyright 2017-2027 商轶龙
 * @license ZPL (http://zpl.pub/v12)
 * @date 文件创建日期 2018-09-17 19:12
 */
public class TypeConvertorFactory {

    @NotNull
    public static TypeConvertor getTypeConvertor(DbTypeEnum type) {
        switch (type) {
            case MYSQL:
                return new MysqlTypeConvertor();
            case SQLITE:
                // 暂无
            case ORACLE:
                // 暂无
            default:
                Asserts.throwException("类型暂不支持");
                return null;
        }
    }
}
