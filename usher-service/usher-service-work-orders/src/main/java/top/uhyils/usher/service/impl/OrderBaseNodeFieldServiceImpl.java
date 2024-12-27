package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderBaseNodeFieldAssembler;
import top.uhyils.usher.pojo.DO.OrderBaseNodeFieldDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeField;
import top.uhyils.usher.repository.OrderBaseNodeFieldRepository;
import top.uhyils.usher.service.OrderBaseNodeFieldService;

/**
 * 工单节点属性样例表(OrderBaseNodeField)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分01秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_base_node_field"})
public class OrderBaseNodeFieldServiceImpl extends AbstractDoService<OrderBaseNodeFieldDO, OrderBaseNodeField, OrderBaseNodeFieldDTO, OrderBaseNodeFieldRepository, OrderBaseNodeFieldAssembler> implements OrderBaseNodeFieldService {

    public OrderBaseNodeFieldServiceImpl(OrderBaseNodeFieldAssembler assembler, OrderBaseNodeFieldRepository repository) {
        super(assembler, repository);
    }


}
