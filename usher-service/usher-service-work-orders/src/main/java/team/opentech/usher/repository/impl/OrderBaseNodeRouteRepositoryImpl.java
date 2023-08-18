package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderBaseNodeRouteAssembler;
import team.opentech.usher.dao.OrderBaseNodeRouteDao;
import team.opentech.usher.pojo.DO.OrderBaseNodeRouteDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderBaseNodeRoute;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.OrderBaseNodeRouteRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;
import java.util.stream.Collectors;


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
    public List<OrderBaseNodeRoute> findNodeRouteByNodes(List<Identifier> nodeIds) {
        List<OrderBaseNodeRouteDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds.stream().map(Identifier::getId).collect(Collectors.toList()));
        return assembler.listToEntity(byOrderNodeIds);
    }
}
