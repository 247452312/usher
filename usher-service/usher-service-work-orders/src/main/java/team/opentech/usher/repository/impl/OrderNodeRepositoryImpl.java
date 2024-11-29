package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderNodeAssembler;
import team.opentech.usher.dao.OrderNodeDao;
import team.opentech.usher.enums.OrderNodeResultTypeEnum;
import team.opentech.usher.enums.OrderNodeStatusEnum;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DTO.OrderNodeDTO;
import team.opentech.usher.pojo.entity.OrderNode;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import team.opentech.usher.util.Asserts;


/**
 * 工单节点样例表(OrderNode)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分21秒
 */
@Repository
public class OrderNodeRepositoryImpl extends AbstractRepository<OrderNode, OrderNodeDO, OrderNodeDao, OrderNodeDTO, OrderNodeAssembler> implements OrderNodeRepository {

    protected OrderNodeRepositoryImpl(OrderNodeAssembler convert, OrderNodeDao dao) {
        super(convert, dao);
    }


    @Override
    public void makeOrderFault(Long orderNodeId, String msg) {
        dao.makeOrderFault(orderNodeId, OrderNodeStatusEnum.FAULT.getCode(), OrderNodeResultTypeEnum.FAULT.getCode(), msg);
    }

    @Override
    public OrderNode findNext(OrderNode orderNode) {
        OrderNodeDO nextNodeByNodeAndResult = dao.getNextNodeByNodeAndResult(orderNode.getUnique().orElseThrow(Asserts::throwOptionalException), orderNode.toData().map(OrderNodeDO::getResultId).orElseThrow(Asserts::throwOptionalException));
        return assembler.toEntity(nextNodeByNodeAndResult);
    }

}
