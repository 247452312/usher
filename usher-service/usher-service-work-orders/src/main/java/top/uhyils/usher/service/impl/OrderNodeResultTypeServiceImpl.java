package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderNodeResultTypeAssembler;
import top.uhyils.usher.pojo.DO.OrderNodeResultTypeDO;
import top.uhyils.usher.pojo.DTO.OrderNodeResultTypeDTO;
import top.uhyils.usher.pojo.entity.OrderNodeResultType;
import top.uhyils.usher.repository.OrderNodeResultTypeRepository;
import top.uhyils.usher.service.OrderNodeResultTypeService;

/**
 * 工单节点处理结果样例表(OrderNodeResultType)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分31秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_node_result_type"})
public class OrderNodeResultTypeServiceImpl extends AbstractDoService<OrderNodeResultTypeDO, OrderNodeResultType, OrderNodeResultTypeDTO, OrderNodeResultTypeRepository, OrderNodeResultTypeAssembler> implements OrderNodeResultTypeService {

    public OrderNodeResultTypeServiceImpl(OrderNodeResultTypeAssembler assembler, OrderNodeResultTypeRepository repository) {
        super(assembler, repository);
    }


}
