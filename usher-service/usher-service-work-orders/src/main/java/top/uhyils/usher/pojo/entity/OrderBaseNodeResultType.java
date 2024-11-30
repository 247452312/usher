package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分04秒
 */
public class OrderBaseNodeResultType extends AbstractDoEntity<OrderBaseNodeResultTypeDO> {

    @Default
    public OrderBaseNodeResultType(OrderBaseNodeResultTypeDO data) {
        super(data);
    }

    public OrderBaseNodeResultType(Long id) {
        super(id, new OrderBaseNodeResultTypeDO());
    }

}
