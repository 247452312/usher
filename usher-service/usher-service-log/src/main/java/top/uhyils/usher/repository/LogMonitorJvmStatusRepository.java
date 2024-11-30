package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.LogMonitorJvmStatusDO;
import top.uhyils.usher.pojo.entity.LogMonitor;
import top.uhyils.usher.pojo.entity.LogMonitorJvmStatus;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
