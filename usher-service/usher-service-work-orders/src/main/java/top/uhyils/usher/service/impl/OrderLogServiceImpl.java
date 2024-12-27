package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.OrderLogAssembler;
import top.uhyils.usher.pojo.DO.OrderLogDO;
import top.uhyils.usher.pojo.DTO.OrderLogDTO;
import top.uhyils.usher.pojo.entity.OrderLog;
import top.uhyils.usher.repository.OrderLogRepository;
import top.uhyils.usher.service.OrderLogService;

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
