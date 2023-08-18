package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderLogAssembler;
import team.opentech.usher.pojo.DO.OrderLogDO;
import team.opentech.usher.pojo.DTO.OrderLogDTO;
import team.opentech.usher.pojo.entity.OrderLog;
import team.opentech.usher.repository.OrderLogRepository;
import team.opentech.usher.service.OrderLogService;
import org.springframework.stereotype.Service;

/**
 * 日志表(OrderLog)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分18秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_log"})
public class OrderLogServiceImpl extends AbstractDoService<OrderLogDO, OrderLog, OrderLogDTO, OrderLogRepository, OrderLogAssembler> implements OrderLogService {

    public OrderLogServiceImpl(OrderLogAssembler assembler, OrderLogRepository repository) {
        super(assembler, repository);
    }


}
