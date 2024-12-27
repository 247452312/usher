package top.uhyils.usher.pojo.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.uhyils.usher.enums.ServiceQualityEnum;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.entity.base.AbstractEntity;
import top.uhyils.usher.repository.LogMonitorJvmStatusRepository;
import top.uhyils.usher.repository.LogMonitorRepository;
import top.uhyils.usher.repository.TraceInfoRepository;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 20时07分
 */
public class OnlineMonitors extends AbstractEntity {

    private final List<LogMonitor> logMonitors;

    private Long webRequestCount;

    private Long rpcExecuteCount;

    /**
     * 现在正在运行的微服务的最早时间
     */
    private Long earlyStartTime;

    public OnlineMonitors(List<LogMonitor> logMonitors) {
        this.logMonitors = logMonitors;
    }

    public OnlineMonitors(LogMonitorRepository rep) {
        this.logMonitors = rep.analysisOnlineService();
    }


    public void initServiceQuality(LogMonitorJvmStatusRepository logMonitorJvmStatusRepository) {
        for (LogMonitor logMonitor : logMonitors) {
            logMonitor.initServiceQuality(logMonitorJvmStatusRepository);
        }
    }

    public void initWebRequestCount(TraceInfoRepository traceInfoRepository) {
        if (webRequestCount != null) {
            return;
        }
        initEarlyStartTime();
        this.webRequestCount = traceInfoRepository.findWebRequestCount(this);

    }

    public Long earlyStartTime() {
        initEarlyStartTime();
        return earlyStartTime;
    }

    public void initRpcExecuteCount(TraceInfoRepository repository) {
        if (rpcExecuteCount != null) {
            return;
        }
        initEarlyStartTime();
        this.rpcExecuteCount = repository.findRpcExecuteCount(this);
    }

    public JvmDataStatisticsDTO outputJvmDataStatictics() {
        Map<Long, List<ServiceQualityEnum>> map = new HashMap<>(logMonitors.size());
        for (LogMonitor logMonitor : logMonitors) {
            List<ServiceQualityEnum> qualities = logMonitor.qualitys();
            Long id = logMonitor.id();
            map.put(id, qualities);
        }
        return JvmDataStatisticsDTO.build(logMonitors.size(), map, webRequestCount, rpcExecuteCount);
    }

    public JvmInfoLogDTO makeEchart(LogMonitorJvmStatusRepository repository) {
        Map<String, List> map = new HashMap<>(logMonitors.size());
        long now = System.currentTimeMillis();
        for (LogMonitor logMonitorEntity : logMonitors) {
            /*初始化状态*/
            logMonitorEntity.initStatuses(repository);
            List echart = logMonitorEntity.makeEchart(now);
            map.put(logMonitorEntity.echartKey(), echart);
        }
        return JvmInfoLogDTO.build(map);
    }

    private void initEarlyStartTime() {
        if (earlyStartTime != null) {
            return;
        }
        // 由于无法分辨前台发送请求是哪一个服务的 所以这里以现在正在运行的jvm最早启动时间为开始时间
        Long firstStartTile = System.currentTimeMillis();
        for (LogMonitor logMonitorEntity : logMonitors) {
            if (logMonitorEntity.startTime() < firstStartTile) {
                this.earlyStartTime = logMonitorEntity.startTime();
            }
        }
    }
}
