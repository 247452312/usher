package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.TraceLogAssembler;
import top.uhyils.usher.pojo.DO.TraceLogDO;
import top.uhyils.usher.pojo.DTO.TraceLogDTO;
import top.uhyils.usher.pojo.entity.TraceLog;
import top.uhyils.usher.repository.TraceLogRepository;
import top.uhyils.usher.service.TraceLogService;

/**
 * (TraceLog)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Service
@ReadWriteMark(tables = {"sys_trace_log"})
public class TraceLogServiceImpl extends AbstractDoService<TraceLogDO, TraceLog, TraceLogDTO, TraceLogRepository, TraceLogAssembler> implements TraceLogService {

    public TraceLogServiceImpl(TraceLogAssembler assembler, TraceLogRepository repository) {
        super(assembler, repository);
    }

}
