package top.uhyils.usher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.LogMonitorAssembler;
import top.uhyils.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import top.uhyils.usher.pojo.DO.LogMonitorDO;
import top.uhyils.usher.pojo.DTO.LogMonitorDTO;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.entity.LogMonitor;
import top.uhyils.usher.pojo.entity.OnlineMonitors;
import top.uhyils.usher.repository.LogMonitorJvmStatusRepository;
import top.uhyils.usher.repository.LogMonitorRepository;
import top.uhyils.usher.repository.TraceInfoRepository;
import top.uhyils.usher.service.LogMonitorService;

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

    @Autowired
    private LogMonitorJvmStatusRepository logMonitorJvmStatusRepository;

    @Autowired
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
        // 接收到jvm启动信息: 如果已经启动了,则代表重复接收 直接返回 注意 这里的重复一定是重复接收,因为判断重复的时候用的是启动时的时间戳. 不存在重复的情况. 如果启动信息重复, 说明状态信息也是重复的. 不再接收
        if (logMonitor.checkMonitorRepeat(rep)) {
            return;
        }

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
