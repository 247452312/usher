package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderNodeRouteAssembler;
import top.uhyils.usher.pojo.DO.OrderNodeRouteDO;
import top.uhyils.usher.pojo.DTO.OrderNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderNodeRoute;
import top.uhyils.usher.repository.OrderNodeRouteRepository;
import top.uhyils.usher.service.OrderNodeRouteService;

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
