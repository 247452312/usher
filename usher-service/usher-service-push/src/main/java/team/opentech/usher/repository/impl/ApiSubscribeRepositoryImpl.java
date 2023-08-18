package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.ApiSubscribeAssembler;
import team.opentech.usher.dao.ApiSubscribeDao;
import team.opentech.usher.pojo.DO.ApiSubscribeDO;
import team.opentech.usher.pojo.DTO.ApiSubscribeDTO;
import team.opentech.usher.pojo.entity.ApiSubscribe;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.ApiSubscribeRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


/**
 * api订阅表(ApiSubscribe)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分54秒
 */
@Repository
public class ApiSubscribeRepositoryImpl extends AbstractRepository<ApiSubscribe, ApiSubscribeDO, ApiSubscribeDao, ApiSubscribeDTO, ApiSubscribeAssembler> implements ApiSubscribeRepository {

    protected ApiSubscribeRepositoryImpl(ApiSubscribeAssembler convert, ApiSubscribeDao dao) {
        super(convert, dao);
    }


    @Override
    public List<ApiSubscribe> findByGroupAndUserId(Identifier groupId, Identifier userId) {
        List<ApiSubscribeDO> apiSubscribeDOS = dao.getByGroupAndUserId(groupId.getId(), userId.getId());
        return assembler.listToEntity(apiSubscribeDOS);
    }

    @Override
    public List<ApiSubscribe> findByGroupId(Identifier groupId) {
        List<ApiSubscribeDO> subscribeDOS = dao.getByGroupId(groupId.getId());
        return assembler.listToEntity(subscribeDOS);
    }

    @Override
    public List<ApiSubscribe> findAll() {
        List<ApiSubscribeDO> list = dao.getAll();
        return assembler.listToEntity(list);
    }

    @Override
    public List<ApiSubscribe> findByCron(String cron) {
        List<ApiSubscribeDO> byCron = dao.getByCron(cron);
        return assembler.listToEntity(byCron);
    }
}
