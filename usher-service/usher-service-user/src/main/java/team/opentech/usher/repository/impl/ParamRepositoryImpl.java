package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ParamAssembler;
import team.opentech.usher.dao.ParamDao;
import team.opentech.usher.pojo.DO.ParamDO;
import team.opentech.usher.pojo.DTO.ParamDTO;
import team.opentech.usher.pojo.entity.Param;
import team.opentech.usher.redis.RedisPool;
import team.opentech.usher.redis.param.SysParamEnum;
import team.opentech.usher.redis.param.SystemParamContext;
import team.opentech.usher.repository.ParamRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;
import java.util.Optional;
import javax.annotation.Resource;


/**
 * 系统参数表(Param)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
@Repository
public class ParamRepositoryImpl extends AbstractRepository<Param, ParamDO, ParamDao, ParamDTO, ParamAssembler> implements ParamRepository {

    @Resource
    private RedisPool redisPool;

    protected ParamRepositoryImpl(ParamAssembler convert, ParamDao dao) {
        super(convert, dao);
    }

    @Override
    public List<Param> findAllGlobalParam() {
        LambdaQueryWrapper<ParamDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ParamDO::getUserId, SystemParamContext.REDIS_PARAM_SYSTEM_USER_ID);
        List<ParamDO> paramDOs = dao.selectList(queryWrapper);
        return assembler.listToEntity(paramDOs);
    }

    @Override
    public void flushParam(Param param) {
        ParamDO paramDO = param.toData().orElseThrow(() -> Asserts.makeException("刷新系统参数失败"));
        String key = paramDO.getKey();
        Optional<SysParamEnum> sysParamEnumOpt = SysParamEnum.parseEnum(key);
        Asserts.assertTrue(sysParamEnumOpt.isPresent(), "参数键值不存在");

        SysParamEnum sysParamEnum = sysParamEnumOpt.get();
        sysParamEnum.flush(paramDO.getUserId(), paramDO.getValue(), redisPool);
    }
}
