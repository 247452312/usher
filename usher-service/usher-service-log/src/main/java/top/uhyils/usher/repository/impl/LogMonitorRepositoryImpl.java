package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.LogMonitorAssembler;
import top.uhyils.usher.dao.LogMonitorDao;
import top.uhyils.usher.mq.pojo.mqinfo.JvmUniqueMark;
import top.uhyils.usher.pojo.DO.LogMonitorDO;
import top.uhyils.usher.pojo.DTO.LogMonitorDTO;
import top.uhyils.usher.pojo.entity.LogMonitor;
import top.uhyils.usher.pojo.entity.LogMonitorJvmStatus;
import top.uhyils.usher.repository.LogMonitorRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * JVM日志表(LogMonitor)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@Repository
public class LogMonitorRepositoryImpl extends AbstractRepository<LogMonitor, LogMonitorDO, LogMonitorDao, LogMonitorDTO, LogMonitorAssembler> implements LogMonitorRepository {


    protected LogMonitorRepositoryImpl(LogMonitorAssembler convert, LogMonitorDao dao) {
        super(convert, dao);
    }


    @Override
    public List<LogMonitor> analysisOnlineService() {
        LambdaQueryWrapper<LogMonitorDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ge(LogMonitorDO::getEndTime, System.currentTimeMillis());
        List<LogMonitorDO> onlineService = dao.selectList(queryWrapper);
        return assembler.listToEntity(onlineService);
    }

    @Override
    public Integer checkMonitorRepeat(JvmUniqueMark unique) {
        return dao.checkMonitorRepeat(unique);
    }

    @Override
    public void changeMonitorThatRepeatByIpAndName(String serviceName, String ip, long currentTimeMillis) {
        dao.updateMonitorThatRepeatByIpAndName(serviceName, ip, currentTimeMillis);
    }

    @Override
    public void changeEndTimeLag(LogMonitorJvmStatus status, long realEndTime) {
        dao.changeEndTime(status.fid(), realEndTime);
    }

    @Override
    public Long getIdByUnique(JvmUniqueMark unique) {
        return dao.getIdByJvmUniqueMark(unique);
    }

}
