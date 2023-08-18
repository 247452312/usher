package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ApiDO;
import team.opentech.usher.pojo.entity.Api;
import team.opentech.usher.pojo.entity.ApiGroup;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * api表(Api)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分46秒
 */
public interface ApiRepository extends BaseEntityRepository<ApiDO, Api> {


    /**
     * 根据groupId获取
     *
     * @param groupId
     *
     * @return
     */
    List<Api> findByGroupId(Identifier groupId);

    /**
     * 根据groupId删除
     *
     * @param groupId
     */
    Integer removeApiByGroup(ApiGroup groupId);

    /**
     * 获取全部
     *
     * @return
     */
    List<Api> findAll();
}
