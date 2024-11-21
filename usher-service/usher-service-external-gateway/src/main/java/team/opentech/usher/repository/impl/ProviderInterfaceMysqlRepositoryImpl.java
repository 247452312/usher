package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ProviderInterfaceMysqlAssembler;
import team.opentech.usher.dao.ProviderInterfaceMysqlDao;
import team.opentech.usher.pojo.DO.ProviderInterfaceMysqlDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import team.opentech.usher.pojo.entity.ProviderExample;
import team.opentech.usher.pojo.entity.ProviderInterfaceMysql;
import team.opentech.usher.repository.ProviderInterfaceMysqlRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
