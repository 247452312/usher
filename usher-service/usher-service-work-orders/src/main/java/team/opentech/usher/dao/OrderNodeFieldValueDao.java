package team.opentech.usher.dao;

import team.opentech.usher.dao.base.DefaultDao;
import team.opentech.usher.pojo.DO.OrderNodeFieldValueDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单节点属性真实值表(OrderNodeFieldValue)表 数据库访问层
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年11月15日 16时16分06秒
 */
@Mapper
public interface OrderNodeFieldValueDao extends DefaultDao<OrderNodeFieldValueDO> {


    /**
     * 根据工单节点id们获取对应的所有属性的值
     *
     * @param fieldIds
     *
     * @return
     */
    List<OrderNodeFieldValueDO> getByOrderFieldIds(@Param("fieldIds") List<Long> fieldIds);
}
