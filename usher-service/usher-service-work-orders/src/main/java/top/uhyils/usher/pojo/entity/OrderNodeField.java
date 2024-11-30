package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.OrderNodeFieldDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 工单节点属性样例表(OrderNodeField)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分23秒
 */
public class OrderNodeField extends AbstractDoEntity<OrderNodeFieldDO> {

    @Default
    public OrderNodeField(OrderNodeFieldDO data) {
        super(data);
    }

    public OrderNodeField(Long id) {
        super(id, new OrderNodeFieldDO());
    }

}
