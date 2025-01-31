package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
@Mapper
public interface OrderNodeRouteDao extends DefaultDao<OrderNodeRouteDO> {


    /**
     * 根据来源工单节点id获取所有的路由
     *
     * @param prevOrderNodeId
     *
     * @return
     */
    List<OrderNodeRouteDO> getByPrevOrderNodeId(Long prevOrderNodeId);

    /**
     * 根据节点id批量删除路由
     *
     * @param ids
     * @param updateUser
     * @param updateDate
     *
     * @return
     */
    Integer deleteByNodeIds(List<Long> ids, Long updateUser, Integer updateDate);

    /**
     * 根据工单节点们获取对应的路由
     *
     * @param orderNodeIds
     *
     * @return
     */
    List<OrderNodeRouteDO> getByOrderNodeIds(@Param("orderNodeIds") List<Long> orderNodeIds);
}
