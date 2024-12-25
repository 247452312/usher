package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.mysql.enums.FieldTypeEnum;
import top.uhyils.usher.pojo.DO.ProviderInterfaceParamDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.util.Asserts;

/**
 * 接口参数表(ProviderInterfaceParam)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年09月09日 15时45分
 */
public class ProviderInterfaceParam extends AbstractDoEntity<ProviderInterfaceParamDO> {

    @Default
    public ProviderInterfaceParam(ProviderInterfaceParamDO data) {
        super(data);
    }

    public ProviderInterfaceParam(Long id) {
        super(id, new ProviderInterfaceParamDO());
    }

    public String name() {
        return toDataAndValidate().getName();
    }

    public FieldTypeEnum type() {
        Integer type = toDataAndValidate().getType();
        switch (type) {
            case 1:
                return FieldTypeEnum.FIELD_TYPE_VARCHAR;
            case 2:
                return FieldTypeEnum.FIELD_TYPE_INT24;
            default:
                Asserts.throwException("未找到的参数类型");
                return null;
        }

    }

}
