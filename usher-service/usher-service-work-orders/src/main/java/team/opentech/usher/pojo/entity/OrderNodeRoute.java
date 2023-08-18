package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分33秒
 */
public class OrderNodeRoute extends AbstractDoEntity<OrderNodeRouteDO> {

    @Default
    public OrderNodeRoute(OrderNodeRouteDO data) {
        super(data);
    }

    public OrderNodeRoute(Long id) {
        super(id, new OrderNodeRouteDO());
    }

}
