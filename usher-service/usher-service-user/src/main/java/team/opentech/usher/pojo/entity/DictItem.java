package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.DictItemDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 数据字典子项(DictItem)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月27日 08时32分44秒
 */
public class DictItem extends AbstractDoEntity<DictItemDO> {

    @Default
    public DictItem(DictItemDO data) {
        super(data);
    }


    public DictItem(Long id) {
        super(id, new DictItemDO());
    }


}
