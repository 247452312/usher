package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderNodeFieldAssembler;
import team.opentech.usher.pojo.DO.OrderNodeFieldDO;
import team.opentech.usher.pojo.DTO.OrderNodeFieldDTO;
import team.opentech.usher.pojo.entity.OrderNodeField;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.service.OrderNodeFieldService;
import org.springframework.stereotype.Service;

/**
 * 工单节点属性样例表(OrderNodeField)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分25秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_node_field"})
public class OrderNodeFieldServiceImpl extends AbstractDoService<OrderNodeFieldDO, OrderNodeField, OrderNodeFieldDTO, OrderNodeFieldRepository, OrderNodeFieldAssembler> implements OrderNodeFieldService {

    public OrderNodeFieldServiceImpl(OrderNodeFieldAssembler assembler, OrderNodeFieldRepository repository) {
        super(assembler, repository);
    }


}
