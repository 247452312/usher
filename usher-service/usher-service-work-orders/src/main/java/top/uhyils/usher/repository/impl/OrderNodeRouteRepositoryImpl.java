package top.uhyils.usher.repository.impl;

import java.util.Arrays;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderNodeRouteAssembler;
import top.uhyils.usher.dao.OrderNodeRouteDao;
import top.uhyils.usher.pojo.DO.OrderNodeRouteDO;
import top.uhyils.usher.pojo.DTO.OrderNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderNodeRoute;
import top.uhyils.usher.repository.OrderNodeRouteRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 节点间关联路由样例表(OrderNodeRoute)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分34秒
 */
@Repository
public class OrderNodeRouteRepositoryImpl extends AbstractRepository<OrderNodeRoute, OrderNodeRouteDO, OrderNodeRouteDao, OrderNodeRouteDTO, OrderNodeRouteAssembler> implements OrderNodeRouteRepository {

    protected OrderNodeRouteRepositoryImpl(OrderNodeRouteAssembler convert, OrderNodeRouteDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderNodeRoute> findByNodeId(Long nodeId) {
        List<OrderNodeRouteDO> byOrderNodeIds = dao.getByOrderNodeIds(Arrays.asList(nodeId));
        return assembler.listToEntity(byOrderNodeIds);
    }

    @Override
    public List<OrderNodeRoute> findByNodeIds(List<Long> nodeIds) {
        List<OrderNodeRouteDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds);
        return assembler.listToEntity(byOrderNodeIds);
    }
}
