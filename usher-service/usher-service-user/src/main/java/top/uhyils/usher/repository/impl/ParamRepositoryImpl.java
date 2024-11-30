package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ParamAssembler;
import top.uhyils.usher.dao.ParamDao;
import top.uhyils.usher.pojo.DO.ParamDO;
import top.uhyils.usher.pojo.DTO.ParamDTO;
import top.uhyils.usher.pojo.entity.Param;
import top.uhyils.usher.redis.RedisPool;
import top.uhyils.usher.redis.param.SysParamEnum;
import top.uhyils.usher.redis.param.SystemParamContext;
import top.uhyils.usher.repository.ParamRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


/**
 * 系统参数表(Param)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
@Repository
public class ParamRepositoryImpl extends AbstractRepository<Param, ParamDO, ParamDao, ParamDTO, ParamAssembler> implements ParamRepository {

    @Autowired
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
