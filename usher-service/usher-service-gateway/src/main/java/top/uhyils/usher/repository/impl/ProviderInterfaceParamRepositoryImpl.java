package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ProviderInterfaceParamAssembler;
import top.uhyils.usher.dao.ProviderInterfaceParamDao;
import top.uhyils.usher.pojo.DO.ProviderInterfaceParamDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceParamDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceParam;
import top.uhyils.usher.repository.ProviderInterfaceParamRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
    public List<ProviderInterfaceParam> findByInterfaceId(Long interfaceId) {
        LambdaQueryWrapper<ProviderInterfaceParamDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceParamDO::getProviderInterfaceId, interfaceId);
        return assembler.listToEntity(dao.selectList(queryWrapper));
    }

}
