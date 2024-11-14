package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ProviderInterfaceRpcAssembler;
import team.opentech.usher.dao.ProviderInterfaceRpcDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceRpcDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import team.opentech.usher.pojo.entity.ProviderExample;
import team.opentech.usher.pojo.entity.ProviderInterfaceRpc;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ProviderInterfaceRpcRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * http接口子表(ProviderInterfaceRpc)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class ProviderInterfaceRpcRepositoryImpl extends AbstractRepository<ProviderInterfaceRpc, ProviderInterfaceRpcDO, ProviderInterfaceRpcDao, ProviderInterfaceRpcDTO, ProviderInterfaceRpcAssembler> implements ProviderInterfaceRpcRepository {

    protected ProviderInterfaceRpcRepositoryImpl(ProviderInterfaceRpcAssembler convert, ProviderInterfaceRpcDao dao) {
        super(convert, dao);
    }


    @Override
    public ProviderExample findByProviderId(Identifier id) {
        LambdaQueryWrapper<ProviderInterfaceRpcDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceRpcDO::getFid, id.getId());
        ProviderInterfaceRpcDO little = dao.selectOne(queryWrapper);
        return assembler.toEntity(little);
    }
}
