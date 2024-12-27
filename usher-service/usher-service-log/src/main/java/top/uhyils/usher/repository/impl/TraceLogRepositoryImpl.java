package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.TraceLogAssembler;
import top.uhyils.usher.dao.TraceLogDao;
import top.uhyils.usher.pojo.DO.TraceLogDO;
import top.uhyils.usher.pojo.DTO.TraceLogDTO;
import top.uhyils.usher.pojo.entity.TraceLog;
import top.uhyils.usher.repository.TraceLogRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
