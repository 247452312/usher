package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OrderApiDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 节点api表(OrderApi)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时58分45秒
 */
public class OrderApi extends AbstractDoEntity<OrderApiDO> {

    @Default
    public OrderApi(OrderApiDO data) {
        super(data);
    }

    public OrderApi(Long id) {
        super(id, new OrderApiDO());
    }

}
