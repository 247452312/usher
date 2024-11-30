package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderNodeFieldAssembler;
import top.uhyils.usher.pojo.DO.OrderNodeFieldDO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderNodeField;
import top.uhyils.usher.repository.OrderNodeFieldRepository;
import top.uhyils.usher.service.OrderNodeFieldService;

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
