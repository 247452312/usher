package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderNodeRouteAssembler;
import team.opentech.usher.dao.OrderNodeRouteDao;
import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderNodeRoute;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.Arrays;
import java.util.List;


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
