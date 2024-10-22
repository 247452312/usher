package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OrderBaseNodeRouteDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分07秒
 */
public class OrderBaseNodeRoute extends AbstractDoEntity<OrderBaseNodeRouteDO> {

    @Default
    public OrderBaseNodeRoute(OrderBaseNodeRouteDO data) {
        super(data);
    }

    public OrderBaseNodeRoute(Long id) {
        super(id, new OrderBaseNodeRouteDO());
    }

}
