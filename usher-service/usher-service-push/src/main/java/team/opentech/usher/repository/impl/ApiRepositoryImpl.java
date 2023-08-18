package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ApiAssembler;
import team.opentech.usher.dao.ApiDao;
import team.opentech.usher.pojo.DO.ApiDO;
import team.opentech.usher.pojo.DO.ApiGroupDO;
import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.entity.Api;
import team.opentech.usher.pojo.entity.ApiGroup;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ApiRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;


/**
 * api表(Api)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分46秒
 */
@Repository
public class ApiRepositoryImpl extends AbstractRepository<Api, ApiDO, ApiDao, ApiDTO, ApiAssembler> implements ApiRepository {

    protected ApiRepositoryImpl(ApiAssembler convert, ApiDao dao) {
        super(convert, dao);
    }


    @Override
    public List<Api> findByGroupId(Identifier groupId) {
        List<ApiDO> groupByGroupId = dao.getGroupByGroupId(groupId.getId());
        return assembler.listToEntity(groupByGroupId);
    }

    @Override
    public Integer removeApiByGroup(ApiGroup groupId) {
        ApiGroupDO apiGroupDO = groupId.toData().orElseThrow(Asserts::throwOptionalException);
        apiGroupDO.preUpdate();
        return dao.deleteAllByGroup(apiGroupDO);
    }

    @Override
    public List<Api> findAll() {
        List<ApiDO> all = dao.getAll();
        return assembler.listToEntity(all);
    }
}
