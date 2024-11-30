package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderBaseNodeFieldDO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeField;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 工单节点属性样例表(OrderBaseNodeField)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分01秒
 */
public interface OrderBaseNodeFieldRepository extends BaseEntityRepository<OrderBaseNodeFieldDO, OrderBaseNodeField> {


    /**
     * 根据节点id获取节点属性
     *
     * @param nodeIds
     *
     * @return
     */
    List<OrderBaseNodeField> findNodeFieldByNodes(List<Long> nodeIds);
}
