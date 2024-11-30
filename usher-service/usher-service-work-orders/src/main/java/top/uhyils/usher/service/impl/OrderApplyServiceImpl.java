package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderApplyAssembler;
import top.uhyils.usher.pojo.DO.OrderApplyDO;
import top.uhyils.usher.pojo.DTO.OrderApplyDTO;
import top.uhyils.usher.pojo.entity.OrderApply;
import top.uhyils.usher.repository.OrderApplyRepository;
import top.uhyils.usher.service.OrderApplyService;

/**
 * (OrderApply)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分50秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_apply"})
public class OrderApplyServiceImpl extends AbstractDoService<OrderApplyDO, OrderApply, OrderApplyDTO, OrderApplyRepository, OrderApplyAssembler> implements OrderApplyService {

    public OrderApplyServiceImpl(OrderApplyAssembler assembler, OrderApplyRepository repository) {
        super(assembler, repository);
    }


}
