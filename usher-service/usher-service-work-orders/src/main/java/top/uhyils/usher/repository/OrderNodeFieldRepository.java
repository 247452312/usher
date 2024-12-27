package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderNodeFieldDO;
import top.uhyils.usher.pojo.entity.OrderNodeField;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 工单节点属性样例表(OrderNodeField)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分24秒
 */
public interface OrderNodeFieldRepository extends BaseEntityRepository<OrderNodeFieldDO, OrderNodeField> {


    /**
     * 获取node的field
     *
     * @param nodeId
     *
     * @return
     */
    List<OrderNodeField> findByNodeId(Long nodeId);

    /**
     * 获取node
     *
     * @param nodeIds
     *
     * @return
     */
    List<OrderNodeField> findByNodeIds(List<Long> nodeIds);

}
