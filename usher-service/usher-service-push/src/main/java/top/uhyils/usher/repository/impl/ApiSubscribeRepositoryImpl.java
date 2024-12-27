package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.ApiSubscribeAssembler;
import top.uhyils.usher.dao.ApiSubscribeDao;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.DTO.ApiSubscribeDTO;
import top.uhyils.usher.pojo.entity.ApiSubscribe;
import top.uhyils.usher.repository.ApiSubscribeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
    public List<ApiSubscribe> findByGroupAndUserId(Long groupId, Long userId) {
        List<ApiSubscribeDO> apiSubscribeDOS = dao.getByGroupAndUserId(groupId, userId);
        return assembler.listToEntity(apiSubscribeDOS);
    }

    @Override
    public List<ApiSubscribe> findByGroupId(Long groupId) {
        List<ApiSubscribeDO> subscribeDOS = dao.getByGroupId(groupId);
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
