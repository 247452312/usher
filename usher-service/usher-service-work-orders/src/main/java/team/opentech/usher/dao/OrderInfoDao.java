package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.OrderInfoDO;
import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月09日 10时11分
 */
@Mapper
public interface OrderInfoDao extends DefaultDao<OrderInfoDO> {


    /**
     * 获取一个工单的状态
     *
     * @param orderId
     *
     * @return
     */
    Integer getOrderStatusById(Long orderId);

    /**
     * 修改工单状态为等待撤回
     *
     * @param orderId
     * @param code
     */
    void changeOrderStatus(@Param("orderId") Long orderId, @Param("code") Integer code);

    /**
     * 根据类型获取其他工单
     *
     * @param type
     *
     * @return
     */
    ArrayList<OrderInfoDO> getOrderByType(Integer type);
}
