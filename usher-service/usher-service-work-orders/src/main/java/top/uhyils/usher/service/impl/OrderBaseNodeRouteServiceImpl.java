package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderBaseNodeRouteAssembler;
import top.uhyils.usher.pojo.DO.OrderBaseNodeRouteDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeRoute;
import top.uhyils.usher.repository.OrderBaseNodeRouteRepository;
import top.uhyils.usher.service.OrderBaseNodeRouteService;

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
