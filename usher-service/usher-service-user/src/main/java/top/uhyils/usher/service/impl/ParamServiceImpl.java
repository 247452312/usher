package top.uhyils.usher.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ParamAssembler;
import top.uhyils.usher.bus.BusInterface;
import top.uhyils.usher.pojo.DO.ParamDO;
import top.uhyils.usher.pojo.DTO.ParamDTO;
import top.uhyils.usher.pojo.cqe.FlushAllSysEvent;
import top.uhyils.usher.pojo.entity.Param;
import top.uhyils.usher.repository.ParamRepository;
import top.uhyils.usher.service.ParamService;

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
