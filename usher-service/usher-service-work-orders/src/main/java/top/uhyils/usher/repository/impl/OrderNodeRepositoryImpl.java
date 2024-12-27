package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderNodeAssembler;
import top.uhyils.usher.dao.OrderNodeDao;
import top.uhyils.usher.enums.OrderNodeResultTypeEnum;
import top.uhyils.usher.enums.OrderNodeStatusEnum;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.DTO.OrderNodeDTO;
import top.uhyils.usher.pojo.entity.OrderNode;
import top.uhyils.usher.repository.OrderNodeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;


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
