package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderNodeRouteDO;
import top.uhyils.usher.pojo.entity.OrderNodeRoute;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分33秒
 */
public interface OrderNodeRouteRepository extends BaseEntityRepository<OrderNodeRouteDO, OrderNodeRoute> {


    /**
     * 根据node获取路由
     *
     * @param nodeId
     *
     * @return
     */
    List<OrderNodeRoute> findByNodeId(Long nodeId);

    /**
     * 根据节点id获取路由信息
     *
     * @param collect
     *
     * @return
     */
    List<OrderNodeRoute> findByNodeIds(List<Long> collect);
}
