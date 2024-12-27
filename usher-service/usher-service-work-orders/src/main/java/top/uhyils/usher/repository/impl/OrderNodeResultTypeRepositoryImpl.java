package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderNodeResultTypeAssembler;
import top.uhyils.usher.dao.OrderNodeResultTypeDao;
import top.uhyils.usher.pojo.DO.OrderNodeResultTypeDO;
import top.uhyils.usher.pojo.DTO.OrderNodeResultTypeDTO;
import top.uhyils.usher.pojo.entity.OrderNodeResultType;
import top.uhyils.usher.repository.OrderNodeResultTypeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 工单节点处理结果样例表(OrderNodeResultType)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分30秒
 */
@Repository
public class OrderNodeResultTypeRepositoryImpl extends AbstractRepository<OrderNodeResultType, OrderNodeResultTypeDO, OrderNodeResultTypeDao, OrderNodeResultTypeDTO, OrderNodeResultTypeAssembler> implements OrderNodeResultTypeRepository {

    protected OrderNodeResultTypeRepositoryImpl(OrderNodeResultTypeAssembler convert, OrderNodeResultTypeDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderNodeResultType> findByNodeId(Long nodeId) {
        List<OrderNodeResultTypeDO> byOrderNodeId = dao.getByOrderNodeId(nodeId);
        return assembler.listToEntity(byOrderNodeId);
    }

    @Override
    public List<OrderNodeResultType> findByNodeIds(List<Long> nodeIds) {
        List<OrderNodeResultTypeDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds);
        return assembler.listToEntity(byOrderNodeIds);
    }

}
