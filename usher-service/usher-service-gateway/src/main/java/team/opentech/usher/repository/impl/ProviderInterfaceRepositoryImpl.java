package team.opentech.usher.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ProviderInterfaceAssembler;
import team.opentech.usher.dao.ProviderInterfaceDao;
import team.opentech.usher.enums.InvokeTypeEnum;
import team.opentech.usher.pojo.DO.ProviderInterfaceDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceDTO;
import team.opentech.usher.pojo.entity.AbstractDataNode;
import team.opentech.usher.pojo.entity.ProviderExample;
import team.opentech.usher.pojo.entity.ProviderInterface;
import team.opentech.usher.pojo.entity.ProviderInterfaceParam;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ProviderInterfaceHttpRepository;
import team.opentech.usher.repository.ProviderInterfaceMysqlRepository;
import team.opentech.usher.repository.ProviderInterfaceParamRepository;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.repository.ProviderInterfaceRpcRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;
import javax.annotation.Resource;


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
    public List<ProviderInterfaceParam> findParamByInterfaceId(Identifier id) {
        return paramRepository.findByInterfaceId(id);
    }

    @Override
    public ProviderExample findExample(Identifier id, InvokeTypeEnum type) {
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
