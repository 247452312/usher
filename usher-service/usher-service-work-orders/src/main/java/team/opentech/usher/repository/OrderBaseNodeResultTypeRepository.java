package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import team.opentech.usher.pojo.entity.OrderBaseNodeResultType;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

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
    List<OrderBaseNodeResultType> findNodeResultTypeByNodes(List<Identifier> nodeIds);
}
