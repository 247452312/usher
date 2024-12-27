package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.LogMonitorJvmStatusAssembler;
import top.uhyils.usher.dao.LogMonitorJvmStatusDao;
import top.uhyils.usher.pojo.DO.LogMonitorJvmStatusDO;
import top.uhyils.usher.pojo.DTO.LogMonitorJvmStatusDTO;
import top.uhyils.usher.pojo.entity.LogMonitor;
import top.uhyils.usher.pojo.entity.LogMonitorJvmStatus;
import top.uhyils.usher.repository.LogMonitorJvmStatusRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
