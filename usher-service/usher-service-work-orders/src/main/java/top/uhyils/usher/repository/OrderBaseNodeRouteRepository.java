package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderBaseNodeRouteDO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeRoute;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分07秒
 */
public interface OrderBaseNodeRouteRepository extends BaseEntityRepository<OrderBaseNodeRouteDO, OrderBaseNodeRoute> {


    /**
     * 根据节点id获取节点路由
     *
     * @param nodeIds
     *
     * @return
     */
    List<OrderBaseNodeRoute> findNodeRouteByNodes(List<Long> nodeIds);
}
