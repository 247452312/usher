package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ProviderInterfaceMysqlAssembler;
import top.uhyils.usher.dao.ProviderInterfaceMysqlDao;
import top.uhyils.usher.pojo.DO.ProviderInterfaceMysqlDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterfaceMysql;
import top.uhyils.usher.repository.ProviderInterfaceMysqlRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * mysql接口子表(ProviderInterfaceMysql)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class ProviderInterfaceMysqlRepositoryImpl extends AbstractRepository<ProviderInterfaceMysql, ProviderInterfaceMysqlDO, ProviderInterfaceMysqlDao, ProviderInterfaceMysqlDTO, ProviderInterfaceMysqlAssembler> implements ProviderInterfaceMysqlRepository {

    protected ProviderInterfaceMysqlRepositoryImpl(ProviderInterfaceMysqlAssembler convert, ProviderInterfaceMysqlDao dao) {
        super(convert, dao);
    }


    @Override
    public ProviderExample findByProviderId(Long id) {
        LambdaQueryWrapper<ProviderInterfaceMysqlDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceMysqlDO::getFid, id);
        ProviderInterfaceMysqlDO little = dao.selectOne(queryWrapper);
        return assembler.toEntity(little);
    }
}
