package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ParamAssembler;
import team.opentech.usher.bus.BusInterface;
import team.opentech.usher.pojo.DO.ParamDO;
import team.opentech.usher.pojo.DTO.ParamDTO;
import team.opentech.usher.pojo.cqe.FlushAllSysEvent;
import team.opentech.usher.pojo.entity.Param;
import team.opentech.usher.repository.ParamRepository;
import team.opentech.usher.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统参数表(Param)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
@Service
@ReadWriteMark(tables = {"sys_param"})
public class ParamServiceImpl extends AbstractDoService<ParamDO, Param, ParamDTO, ParamRepository, ParamAssembler> implements ParamService {

    @Autowired
    private BusInterface bus;

    public ParamServiceImpl(ParamAssembler assembler, ParamRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Boolean flushAllGlobal(FlushAllSysEvent event) {
        bus.asyncCommitAndPush(event);
        return true;
    }

    @Override
    public Boolean flushAllGlobal() {
        Param param = new Param();
        return param.flushAllGlobal(rep);
    }
}
