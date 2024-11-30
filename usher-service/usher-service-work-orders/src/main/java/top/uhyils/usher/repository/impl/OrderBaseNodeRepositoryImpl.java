package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderBaseNodeAssembler;
import top.uhyils.usher.dao.OrderBaseNodeDao;
import top.uhyils.usher.pojo.DO.OrderBaseNodeDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNode;
import top.uhyils.usher.repository.OrderBaseNodeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 工单节点样例表(OrderBaseNode)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分57秒
 */
@Repository
public class OrderBaseNodeRepositoryImpl extends AbstractRepository<OrderBaseNode, OrderBaseNodeDO, OrderBaseNodeDao, OrderBaseNodeDTO, OrderBaseNodeAssembler> implements OrderBaseNodeRepository {


    protected OrderBaseNodeRepositoryImpl(OrderBaseNodeAssembler convert, OrderBaseNodeDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderBaseNode> findNoHiddenNodeById(Long id) {
        List<OrderBaseNodeDO> noHiddenByOrderId = dao.getNoHiddenByOrderId(id);
        return assembler.listToEntity(noHiddenByOrderId);
    }
}
