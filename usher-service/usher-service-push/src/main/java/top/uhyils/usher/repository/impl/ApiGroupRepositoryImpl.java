package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ApiGroupAssembler;
import top.uhyils.usher.dao.ApiGroupDao;
import top.uhyils.usher.pojo.DO.ApiGroupDO;
import top.uhyils.usher.pojo.DTO.ApiGroupDTO;
import top.uhyils.usher.pojo.entity.ApiGroup;
import top.uhyils.usher.repository.ApiGroupRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
