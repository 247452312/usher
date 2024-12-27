package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.OrderBaseNodeDO;
import top.uhyils.usher.pojo.entity.OrderBaseNode;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 工单节点样例表(OrderBaseNode)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分57秒
 */
public interface OrderBaseNodeRepository extends BaseEntityRepository<OrderBaseNodeDO, OrderBaseNode> {


    /**
     * 根据工单id获取节点
     *
     * @param id
     *
     * @return
     */
    List<OrderBaseNode> findNoHiddenNodeById(Long id);
}
