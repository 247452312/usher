package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderBaseNodeRouteAssembler;
import team.opentech.usher.pojo.DO.OrderBaseNodeRouteDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderBaseNodeRoute;
import team.opentech.usher.repository.OrderBaseNodeRouteRepository;
import team.opentech.usher.service.OrderBaseNodeRouteService;
import org.springframework.stereotype.Service;

/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分08秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_base_node_route"})
public class OrderBaseNodeRouteServiceImpl extends AbstractDoService<OrderBaseNodeRouteDO, OrderBaseNodeRoute, OrderBaseNodeRouteDTO, OrderBaseNodeRouteRepository, OrderBaseNodeRouteAssembler> implements OrderBaseNodeRouteService {

    public OrderBaseNodeRouteServiceImpl(OrderBaseNodeRouteAssembler assembler, OrderBaseNodeRouteRepository repository) {
        super(assembler, repository);
    }


}
