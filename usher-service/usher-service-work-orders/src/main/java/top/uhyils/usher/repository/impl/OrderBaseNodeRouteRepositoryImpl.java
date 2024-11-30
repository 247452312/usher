package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderBaseNodeRouteAssembler;
import top.uhyils.usher.dao.OrderBaseNodeRouteDao;
import top.uhyils.usher.pojo.DO.OrderBaseNodeRouteDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeRoute;
import top.uhyils.usher.repository.OrderBaseNodeRouteRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分08秒
 */
@Repository
public class OrderBaseNodeRouteRepositoryImpl extends AbstractRepository<OrderBaseNodeRoute, OrderBaseNodeRouteDO, OrderBaseNodeRouteDao, OrderBaseNodeRouteDTO, OrderBaseNodeRouteAssembler> implements OrderBaseNodeRouteRepository {

    protected OrderBaseNodeRouteRepositoryImpl(OrderBaseNodeRouteAssembler convert, OrderBaseNodeRouteDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderBaseNodeRoute> findNodeRouteByNodes(List<Long> nodeIds) {
        List<OrderBaseNodeRouteDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds);
        return assembler.listToEntity(byOrderNodeIds);
    }
}
