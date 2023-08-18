package team.opentech.usher.repository;

import team.opentech.usher.mq.pojo.mqinfo.JvmUniqueMark;
import team.opentech.usher.pojo.DO.LogMonitorDO;
import team.opentech.usher.pojo.entity.LogMonitor;
import team.opentech.usher.pojo.entity.LogMonitorJvmStatus;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * JVM日志表(LogMonitor)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public interface LogMonitorRepository extends BaseEntityRepository<LogMonitorDO, LogMonitor> {

    /**
     * 获取在线服务信息
     *
     * @return
     */
    List<LogMonitor> analysisOnlineService();

    /**
     * 检查服务是否存在
     *
     * @param unique
     *
     * @return
     */
    Integer checkMonitorRepeat(JvmUniqueMark unique);

    /**
     * 修改服务的时间到现在
     *
     * @param serviceName
     * @param ip
     * @param currentTimeMillis
     */
    void changeMonitorThatRepeatByIpAndName(String serviceName, String ip, long currentTimeMillis);

    /**
     * 修改微服务的endTime
     *
     * @param logMonitor
     * @param realEndTime
     */
    void changeEndTimeLag(LogMonitorJvmStatus logMonitor, long realEndTime);

    /**
     * 根据unique获取id
     *
     * @param unique
     *
     * @return
     */
    Identifier getIdByUnique(JvmUniqueMark unique);
}
