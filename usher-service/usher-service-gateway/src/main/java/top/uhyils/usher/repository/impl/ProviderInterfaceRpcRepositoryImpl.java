package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ProviderInterfaceRpcAssembler;
import top.uhyils.usher.dao.ProviderInterfaceRpcDao;
import top.uhyils.usher.pojo.DO.ProviderInterfaceRpcDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterfaceRpc;
import top.uhyils.usher.repository.ProviderInterfaceRpcRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
    public ProviderExample findByProviderId(Long id) {
        LambdaQueryWrapper<ProviderInterfaceRpcDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceRpcDO::getFid, id);
        ProviderInterfaceRpcDO little = dao.selectOne(queryWrapper);
        return assembler.toEntity(little);
    }
}
