package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.LogMonitorJvmStatusDO;
import team.opentech.usher.pojo.entity.LogMonitor;
import team.opentech.usher.pojo.entity.LogMonitorJvmStatus;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public interface LogMonitorJvmStatusRepository extends BaseEntityRepository<LogMonitorJvmStatusDO, LogMonitorJvmStatus> {


    /**
     * 列表获取这个服务的从启动开始的JVM状态
     *
     * @param logMonitor
     *
     * @return
     */
    List<LogMonitorJvmStatus> listLogMonitorJvmStatus(LogMonitor logMonitor);
}
