package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ApiAssembler;
import top.uhyils.usher.dao.ApiDao;
import top.uhyils.usher.pojo.DO.ApiDO;
import top.uhyils.usher.pojo.DO.ApiGroupDO;
import top.uhyils.usher.pojo.DTO.ApiDTO;
import top.uhyils.usher.pojo.entity.Api;
import top.uhyils.usher.pojo.entity.ApiGroup;
import top.uhyils.usher.repository.ApiRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


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
    public List<Api> findByGroupId(Long groupId) {
        List<ApiDO> groupByGroupId = dao.getGroupByGroupId(groupId);
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
