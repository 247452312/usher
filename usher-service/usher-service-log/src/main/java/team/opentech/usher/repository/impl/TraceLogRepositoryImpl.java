package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.TraceLogAssembler;
import team.opentech.usher.dao.TraceLogDao;
import team.opentech.usher.pojo.DO.TraceLogDO;
import team.opentech.usher.pojo.DTO.TraceLogDTO;
import team.opentech.usher.pojo.entity.TraceLog;
import team.opentech.usher.repository.TraceLogRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * (TraceLog)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Repository
public class TraceLogRepositoryImpl extends AbstractRepository<TraceLog, TraceLogDO, TraceLogDao, TraceLogDTO, TraceLogAssembler> implements TraceLogRepository {

    protected TraceLogRepositoryImpl(TraceLogAssembler convert, TraceLogDao dao) {
        super(convert, dao);
    }


}
