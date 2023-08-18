package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ApiGroupAssembler;
import team.opentech.usher.dao.ApiGroupDao;
import team.opentech.usher.pojo.DO.ApiGroupDO;
import team.opentech.usher.pojo.DTO.ApiGroupDTO;
import team.opentech.usher.pojo.entity.ApiGroup;
import team.opentech.usher.repository.ApiGroupRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


/**
 * api组表(ApiGroup)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分50秒
 */
@Repository
public class ApiGroupRepositoryImpl extends AbstractRepository<ApiGroup, ApiGroupDO, ApiGroupDao, ApiGroupDTO, ApiGroupAssembler> implements ApiGroupRepository {

    protected ApiGroupRepositoryImpl(ApiGroupAssembler convert, ApiGroupDao dao) {
        super(convert, dao);
    }


    @Override
    public List<ApiGroup> getCanBeSubscribed() {
        List<ApiGroupDO> canBeSubscribed = dao.getCanBeSubscribed();
        return assembler.listToEntity(canBeSubscribed);
    }

    @Override
    public List<ApiGroup> findAll() {
        List<ApiGroupDO> all = dao.getAll();
        return assembler.listToEntity(all);
    }
}
