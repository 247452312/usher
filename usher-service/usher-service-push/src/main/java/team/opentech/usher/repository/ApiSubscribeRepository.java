package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ApiSubscribeDO;
import team.opentech.usher.pojo.entity.ApiSubscribe;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * api订阅表(ApiSubscribe)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分54秒
 */
public interface ApiSubscribeRepository extends BaseEntityRepository<ApiSubscribeDO, ApiSubscribe> {


    /**
     * 获取订阅表
     *
     * @param groupId
     * @param userId
     *
     * @return
     */
    List<ApiSubscribe> findByGroupAndUserId(Identifier groupId, Identifier userId);

    /**
     * 根据groupId获取
     *
     * @param groupId
     *
     * @return
     */
    List<ApiSubscribe> findByGroupId(Identifier groupId);

    /**
     * 获取全部
     *
     * @return
     */
    List<ApiSubscribe> findAll();

    /**
     * 根据规定时间获取
     *
     * @param cron
     *
     * @return
     */
    List<ApiSubscribe> findByCron(String cron);

}
