package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderBaseNodeResultTypeAssembler;
import top.uhyils.usher.dao.OrderBaseNodeResultTypeDao;
import top.uhyils.usher.pojo.DO.OrderBaseNodeResultTypeDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeResultTypeDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeResultType;
import top.uhyils.usher.repository.OrderBaseNodeResultTypeRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
