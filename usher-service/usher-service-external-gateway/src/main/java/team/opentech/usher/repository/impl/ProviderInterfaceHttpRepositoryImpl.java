package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ProviderInterfaceHttpAssembler;
import team.opentech.usher.dao.ProviderInterfaceHttpDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceHttpDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import team.opentech.usher.pojo.entity.ProviderExample;
import team.opentech.usher.pojo.entity.ProviderInterfaceHttp;
import team.opentech.usher.repository.ProviderInterfaceHttpRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * http接口子表(ProviderInterfaceHttp)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class ProviderInterfaceHttpRepositoryImpl extends AbstractRepository<ProviderInterfaceHttp, ProviderInterfaceHttpDO, ProviderInterfaceHttpDao, ProviderInterfaceHttpDTO, ProviderInterfaceHttpAssembler> implements ProviderInterfaceHttpRepository {

    protected ProviderInterfaceHttpRepositoryImpl(ProviderInterfaceHttpAssembler convert, ProviderInterfaceHttpDao dao) {
        super(convert, dao);
    }


    @Override
    public ProviderExample findByProviderId(Long id) {
        LambdaQueryWrapper<ProviderInterfaceHttpDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceHttpDO::getFid, id);
        ProviderInterfaceHttpDO little = dao.selectOne(queryWrapper);
        return assembler.toEntity(little);
    }
}
