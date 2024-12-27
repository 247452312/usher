package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderBaseNodeResultTypeAssembler;
import top.uhyils.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeResultTypeDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeResultType;
import top.uhyils.usher.repository.OrderBaseNodeResultTypeRepository;
import top.uhyils.usher.service.OrderBaseNodeResultTypeService;

/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分05秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_base_node_result_type"})
public class OrderBaseNodeResultTypeServiceImpl extends AbstractDoService<OrderBaseNodeResultTypeDO, OrderBaseNodeResultType, OrderBaseNodeResultTypeDTO, OrderBaseNodeResultTypeRepository, OrderBaseNodeResultTypeAssembler> implements OrderBaseNodeResultTypeService {

    public OrderBaseNodeResultTypeServiceImpl(OrderBaseNodeResultTypeAssembler assembler, OrderBaseNodeResultTypeRepository repository) {
        super(assembler, repository);
    }


}
