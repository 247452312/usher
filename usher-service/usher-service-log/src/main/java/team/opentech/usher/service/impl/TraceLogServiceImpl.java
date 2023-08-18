package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.TraceLogAssembler;
import team.opentech.usher.pojo.DO.TraceLogDO;
import team.opentech.usher.pojo.DTO.TraceLogDTO;
import team.opentech.usher.pojo.entity.TraceLog;
import team.opentech.usher.repository.TraceLogRepository;
import team.opentech.usher.service.TraceLogService;
import org.springframework.stereotype.Service;

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
