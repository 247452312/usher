package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.LogMonitorJvmStatusAssembler;
import team.opentech.usher.dao.LogMonitorJvmStatusDao;
import team.opentech.usher.pojo.DO.LogMonitorJvmStatusDO;
import team.opentech.usher.pojo.DTO.LogMonitorJvmStatusDTO;
import team.opentech.usher.pojo.entity.LogMonitor;
import team.opentech.usher.pojo.entity.LogMonitorJvmStatus;
import team.opentech.usher.repository.LogMonitorJvmStatusRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


/**
 * JVM状态子表(LogMonitorJvmStatus)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@Repository
public class LogMonitorJvmStatusRepositoryImpl extends AbstractRepository<LogMonitorJvmStatus, LogMonitorJvmStatusDO, LogMonitorJvmStatusDao, LogMonitorJvmStatusDTO, LogMonitorJvmStatusAssembler> implements LogMonitorJvmStatusRepository {

    protected LogMonitorJvmStatusRepositoryImpl(LogMonitorJvmStatusAssembler convert, LogMonitorJvmStatusDao dao) {
        super(convert, dao);
    }


    @Override
    public List<LogMonitorJvmStatus> listLogMonitorJvmStatus(LogMonitor logMonitor) {
        LambdaQueryWrapper<LogMonitorJvmStatusDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LogMonitorJvmStatusDO::getFid, logMonitor.id());
        queryWrapper.orderByAsc(LogMonitorJvmStatusDO::getTime);
        List<LogMonitorJvmStatusDO> byMonitorId = dao.selectList(queryWrapper);
        return assembler.listToEntity(byMonitorId);
    }
}
