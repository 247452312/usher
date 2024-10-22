package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderNodeRouteAssembler;
import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderNodeRoute;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.service.OrderNodeRouteService;
import org.springframework.stereotype.Service;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分34秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_node_route"})
public class OrderNodeRouteServiceImpl extends AbstractDoService<OrderNodeRouteDO, OrderNodeRoute, OrderNodeRouteDTO, OrderNodeRouteRepository, OrderNodeRouteAssembler> implements OrderNodeRouteService {

    public OrderNodeRouteServiceImpl(OrderNodeRouteAssembler assembler, OrderNodeRouteRepository repository) {
        super(assembler, repository);
    }


}
