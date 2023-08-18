package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderBaseNodeAssembler;
import team.opentech.usher.pojo.DO.OrderBaseNodeDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeDTO;
import team.opentech.usher.pojo.entity.OrderBaseNode;
import team.opentech.usher.repository.OrderBaseNodeRepository;
import team.opentech.usher.service.OrderBaseNodeService;
import org.springframework.stereotype.Service;

/**
 * 工单节点样例表(OrderBaseNode)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分58秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_base_node"})
public class OrderBaseNodeServiceImpl extends AbstractDoService<OrderBaseNodeDO, OrderBaseNode, OrderBaseNodeDTO, OrderBaseNodeRepository, OrderBaseNodeAssembler> implements OrderBaseNodeService {

    public OrderBaseNodeServiceImpl(OrderBaseNodeAssembler assembler, OrderBaseNodeRepository repository) {
        super(assembler, repository);
    }


}
