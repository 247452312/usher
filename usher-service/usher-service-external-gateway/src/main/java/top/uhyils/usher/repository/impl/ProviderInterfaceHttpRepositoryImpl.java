package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ProviderInterfaceHttpAssembler;
import top.uhyils.usher.dao.ProviderInterfaceHttpDao;
import top.uhyils.usher.pojo.DO.ProviderInterfaceHttpDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterfaceHttp;
import top.uhyils.usher.repository.ProviderInterfaceHttpRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
