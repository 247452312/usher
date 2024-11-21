package team.opentech.usher.repository.impl;

import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderBaseNodeAssembler;
import team.opentech.usher.dao.OrderBaseNodeDao;
import team.opentech.usher.pojo.DO.OrderBaseNodeDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeDTO;
import team.opentech.usher.pojo.entity.OrderBaseNode;
import team.opentech.usher.repository.OrderBaseNodeRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
