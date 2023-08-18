package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderNodeFieldValueAssembler;
import team.opentech.usher.pojo.DO.OrderNodeFieldValueDO;
import team.opentech.usher.pojo.DTO.OrderNodeFieldValueDTO;
import team.opentech.usher.pojo.entity.OrderNodeFieldValue;
import team.opentech.usher.repository.OrderNodeFieldValueRepository;
import team.opentech.usher.service.OrderNodeFieldValueService;
import org.springframework.stereotype.Service;

/**
 * 订单节点属性真实值表(OrderNodeFieldValue)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分28秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_node_field_value"})
public class OrderNodeFieldValueServiceImpl extends AbstractDoService<OrderNodeFieldValueDO, OrderNodeFieldValue, OrderNodeFieldValueDTO, OrderNodeFieldValueRepository, OrderNodeFieldValueAssembler> implements OrderNodeFieldValueService {

    public OrderNodeFieldValueServiceImpl(OrderNodeFieldValueAssembler assembler, OrderNodeFieldValueRepository repository) {
        super(assembler, repository);
    }


}
