package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.ApiSubscribeDO;
import top.uhyils.usher.pojo.entity.ApiSubscribe;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
    List<ApiSubscribe> findByGroupAndUserId(Long groupId, Long userId);

    /**
     * 根据groupId获取
     *
     * @param groupId
     *
     * @return
     */
    List<ApiSubscribe> findByGroupId(Long groupId);

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
