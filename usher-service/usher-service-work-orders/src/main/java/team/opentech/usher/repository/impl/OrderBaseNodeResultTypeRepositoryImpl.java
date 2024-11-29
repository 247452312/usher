package team.opentech.usher.repository.impl;

import java.util.List;
import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderBaseNodeResultTypeAssembler;
import team.opentech.usher.dao.OrderBaseNodeResultTypeDao;
import team.opentech.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeResultTypeDTO;
import team.opentech.usher.pojo.entity.OrderBaseNodeResultType;
import team.opentech.usher.repository.OrderBaseNodeResultTypeRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分04秒
 */
@Repository
public class OrderBaseNodeResultTypeRepositoryImpl extends AbstractRepository<OrderBaseNodeResultType, OrderBaseNodeResultTypeDO, OrderBaseNodeResultTypeDao, OrderBaseNodeResultTypeDTO, OrderBaseNodeResultTypeAssembler> implements OrderBaseNodeResultTypeRepository {

    protected OrderBaseNodeResultTypeRepositoryImpl(OrderBaseNodeResultTypeAssembler convert, OrderBaseNodeResultTypeDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderBaseNodeResultType> findNodeResultTypeByNodes(List<Long> nodeIds) {
        List<OrderBaseNodeResultTypeDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds);
        return assembler.listToEntity(byOrderNodeIds);
    }
}
