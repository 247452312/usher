package team.opentech.usher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.LogMonitorJvmStatusAssembler;
import team.opentech.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import team.opentech.usher.pojo.DO.LogMonitorJvmStatusDO;
import team.opentech.usher.pojo.DTO.LogMonitorJvmStatusDTO;
import team.opentech.usher.pojo.entity.LogMonitorJvmStatus;
import team.opentech.usher.repository.LogMonitorJvmStatusRepository;
import team.opentech.usher.repository.LogMonitorRepository;
import team.opentech.usher.service.LogMonitorJvmStatusService;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@Service
@ReadWriteMark(tables = {"sys_log_monitor_jvm_status"})
public class LogMonitorJvmStatusServiceImpl extends AbstractDoService<LogMonitorJvmStatusDO, LogMonitorJvmStatus, LogMonitorJvmStatusDTO, LogMonitorJvmStatusRepository, LogMonitorJvmStatusAssembler> implements LogMonitorJvmStatusService {


    @Autowired
    private LogMonitorRepository repository;


    public LogMonitorJvmStatusServiceImpl(LogMonitorJvmStatusAssembler assembler, LogMonitorJvmStatusRepository repository) {
        super(assembler, repository);
    }

    @Override
    public void receiveJvmStatusInfo(JvmStatusInfoCommand jvmStatusInfo) {
        LogMonitorJvmStatus logMonitorJvmStatus = assem.jvmStatusInfoToEntity(jvmStatusInfo);
        // 填充父id
        logMonitorJvmStatus.fillFid(repository);
        // 保存状态信息
        logMonitorJvmStatus.saveSelf(rep);
        // 修改主类假想结束时间
        logMonitorJvmStatus.changeEndTimeLag(repository);
    }
}
