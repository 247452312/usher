package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.ApiGroupDO;
import team.opentech.usher.pojo.entity.ApiGroup;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * api组表(ApiGroup)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分50秒
 */
public interface ApiGroupRepository extends BaseEntityRepository<ApiGroupDO, ApiGroup> {


    /**
     * 获取可以被订阅的所有api群
     *
     * @return
     */
    List<ApiGroup> getCanBeSubscribed();

    /**
     * 获取所有
     *
     * @return
     */
    List<ApiGroup> findAll();

}
