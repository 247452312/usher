package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.LogMonitorAssembler;
import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.pojo.DO.LogMonitorDO;
import team.opentech.usher.pojo.DTO.LogMonitorDTO;
import team.opentech.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import team.opentech.usher.pojo.DTO.response.JvmInfoLogDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.entity.LogMonitor;
import team.opentech.usher.pojo.entity.OnlineMonitors;
import team.opentech.usher.repository.LogMonitorJvmStatusRepository;
import team.opentech.usher.repository.LogMonitorRepository;
import team.opentech.usher.repository.TraceInfoRepository;
import team.opentech.usher.service.LogMonitorService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * JVM日志表(LogMonitor)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@Service
@ReadWriteMark(tables = {"sys_log_monitor"})
public class LogMonitorServiceImpl extends AbstractDoService<LogMonitorDO, LogMonitor, LogMonitorDTO, LogMonitorRepository, LogMonitorAssembler> implements LogMonitorService {

    @Resource
    private LogMonitorJvmStatusRepository logMonitorJvmStatusRepository;

    @Resource
    private TraceInfoRepository traceInfoRepository;


    public LogMonitorServiceImpl(LogMonitorAssembler assembler, LogMonitorRepository repository) {
        super(assembler, repository);
    }

    @Override
    public JvmDataStatisticsDTO getJvmDataStatisticsResponse(DefaultCQE request) {
        /* 获取在线服务 */
        OnlineMonitors onlineMonitor = new OnlineMonitors(rep);
        /* 获取服务运行质量(以外关闭的系统,内存溢出风险的系统) */
        onlineMonitor.initServiceQuality(logMonitorJvmStatusRepository);
        /*获取web调用次数*/
        onlineMonitor.initWebRequestCount(traceInfoRepository);
        /*获取rpc调用次数*/
        onlineMonitor.initRpcExecuteCount(traceInfoRepository);
        return onlineMonitor.outputJvmDataStatictics();
    }

    @Override
    public JvmInfoLogDTO getJvmInfoLogResponse(DefaultCQE request) {
        /*获取所有的活着的服务的监控信息*/
        OnlineMonitors onlineMonitors = new OnlineMonitors(rep);
        /*初始化历史状态*/
        onlineMonitors.initServiceQuality(logMonitorJvmStatusRepository);
        /*做echart图*/
        return onlineMonitors.makeEchart(logMonitorJvmStatusRepository);
    }

    @Override
    public void receiveJvmStartInfo(JvmStartInfoCommand jvmStartInfo) {
        LogMonitor logMonitor = assem.jvmStartInfoToLogMonitor(jvmStartInfo);

        logMonitor.checkMonitorRepeat(rep);

        /*查询有没有同样ip 且同样服务名称的,如果有,将endtime设置为现在,表示发现停止的时间*/
        logMonitor.changeMonitorThatRepeatByIpAndName(rep);

        /* 新增JVM启动信息 */
        logMonitor.addSelf(rep);

        /*插入jvmStatus*/
        logMonitor.addStatus(logMonitorJvmStatusRepository);
        /*修改结束时间为假想时间*/
        logMonitor.changeEndTimeLag(rep);

    }
}
