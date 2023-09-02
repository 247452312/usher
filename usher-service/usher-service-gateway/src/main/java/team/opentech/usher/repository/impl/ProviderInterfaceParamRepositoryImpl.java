package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ProviderInterfaceParamAssembler;
import team.opentech.usher.dao.ProviderInterfaceParamDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceParamDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceParamDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceParam;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ProviderInterfaceParamRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 接口参数表(ProviderInterfaceParam)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class ProviderInterfaceParamRepositoryImpl extends AbstractRepository<ProviderInterfaceParam, ProviderInterfaceParamDO, ProviderInterfaceParamDao, ProviderInterfaceParamDTO, ProviderInterfaceParamAssembler> implements ProviderInterfaceParamRepository {

    protected ProviderInterfaceParamRepositoryImpl(ProviderInterfaceParamAssembler convert, ProviderInterfaceParamDao dao) {
        super(convert, dao);
    }


    @Override
    public List<ProviderInterfaceParam> findByInterfaceId(Identifier interfaceId) {
        LambdaQueryWrapper<ProviderInterfaceParamDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceParamDO::getProviderInterfaceId, interfaceId.getId());
        return assembler.listToEntity(dao.selectList(queryWrapper));
    }

}
