package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeResultType;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分04秒
 */
public interface OrderBaseNodeResultTypeRepository extends BaseEntityRepository<OrderBaseNodeResultTypeDO, OrderBaseNodeResultType> {


    /**
     * 根据节点id获取节点结果类型
     *
     * @param nodeIds
     *
     * @return
     */
    List<OrderBaseNodeResultType> findNodeResultTypeByNodes(List<Long> nodeIds);
}
