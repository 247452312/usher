package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderNodeResultTypeAssembler;
import team.opentech.usher.dao.OrderNodeResultTypeDao;
import team.opentech.usher.pojo.DO.OrderNodeResultTypeDO;
import team.opentech.usher.pojo.DTO.OrderNodeResultTypeDTO;
import team.opentech.usher.pojo.entity.OrderNodeResultType;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


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
