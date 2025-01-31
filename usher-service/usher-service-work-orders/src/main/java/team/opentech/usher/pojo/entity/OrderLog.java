package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.OrderLogDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 日志表(OrderLog)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分16秒
 */
public class OrderLog extends AbstractDoEntity<OrderLogDO> {

    @Default
    public OrderLog(OrderLogDO data) {
        super(data);
    }

    public OrderLog(Long id) {
        super(id, new OrderLogDO());
    }

}
