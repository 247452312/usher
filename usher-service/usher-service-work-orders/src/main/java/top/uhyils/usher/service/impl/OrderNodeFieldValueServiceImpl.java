package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderNodeFieldValueAssembler;
import top.uhyils.usher.pojo.DO.OrderNodeFieldValueDO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldValueDTO;
import top.uhyils.usher.pojo.entity.OrderNodeFieldValue;
import top.uhyils.usher.repository.OrderNodeFieldValueRepository;
import top.uhyils.usher.service.OrderNodeFieldValueService;

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
