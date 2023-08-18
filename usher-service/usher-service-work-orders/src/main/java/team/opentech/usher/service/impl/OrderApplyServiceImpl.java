package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderApplyAssembler;
import team.opentech.usher.pojo.DO.OrderApplyDO;
import team.opentech.usher.pojo.DTO.OrderApplyDTO;
import team.opentech.usher.pojo.entity.OrderApply;
import team.opentech.usher.repository.OrderApplyRepository;
import team.opentech.usher.service.OrderApplyService;
import org.springframework.stereotype.Service;

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
