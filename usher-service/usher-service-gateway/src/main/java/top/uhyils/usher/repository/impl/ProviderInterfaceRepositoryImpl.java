package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ProviderInterfaceAssembler;
import top.uhyils.usher.dao.ProviderInterfaceDao;
import top.uhyils.usher.enums.InvokeTypeEnum;
import top.uhyils.usher.pojo.DO.ProviderInterfaceDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceDTO;
import top.uhyils.usher.pojo.entity.AbstractDataNode;
import top.uhyils.usher.pojo.entity.ProviderExample;
import top.uhyils.usher.pojo.entity.ProviderInterface;
import top.uhyils.usher.pojo.entity.ProviderInterfaceParam;
import top.uhyils.usher.repository.ProviderInterfaceHttpRepository;
import top.uhyils.usher.repository.ProviderInterfaceMysqlRepository;
import top.uhyils.usher.repository.ProviderInterfaceParamRepository;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.repository.ProviderInterfaceRpcRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Repository
public class ProviderInterfaceRepositoryImpl extends AbstractRepository<ProviderInterface, ProviderInterfaceDO, ProviderInterfaceDao, ProviderInterfaceDTO, ProviderInterfaceAssembler> implements ProviderInterfaceRepository {

    @Resource
    private ProviderInterfaceParamRepository paramRepository;

    @Resource
    private ProviderInterfaceRpcRepository rpcRepository;

    @Resource
    private ProviderInterfaceHttpRepository httpRepository;

    @Resource
    private ProviderInterfaceMysqlRepository mysqlRepository;

    protected ProviderInterfaceRepositoryImpl(ProviderInterfaceAssembler convert, ProviderInterfaceDao dao) {
        super(convert, dao);
    }


    @Override
    public ProviderInterface find(Integer invokeType, String database, String table) {
        LambdaQueryWrapper<ProviderInterfaceDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceDO::getInvokeType, invokeType);
        queryWrapper.eq(ProviderInterfaceDO::getDatabase, database);
        queryWrapper.eq(ProviderInterfaceDO::getTable, table);
        return assembler.toEntity(dao.selectOne(queryWrapper));
    }

    @Override
    public AbstractDataNode<ProviderInterfaceDO> find(String database, String table) {
        LambdaQueryWrapper<ProviderInterfaceDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProviderInterfaceDO::getDatabase, database);
        queryWrapper.eq(ProviderInterfaceDO::getTable, table);
        ProviderInterfaceDO nodeDO = dao.selectOne(queryWrapper);
        return assembler.toEntity(nodeDO);
    }

    @Override
    public List<ProviderInterfaceParam> findParamByInterfaceId(Long id) {
        return paramRepository.findByInterfaceId(id);
    }

    @Override
    public ProviderExample findExample(Long id, InvokeTypeEnum type) {
        switch (type) {
            case RPC:
                return rpcRepository.findByProviderId(id);
            case HTTP:
                return httpRepository.findByProviderId(id);
            case MYSQL:
                return mysqlRepository.findByProviderId(id);
            default:
                Asserts.throwException("未找到对应类型:{}", type);
                return null;
        }
    }
}
