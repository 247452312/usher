package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderNodeFieldAssembler;
import top.uhyils.usher.dao.OrderNodeFieldDao;
import top.uhyils.usher.pojo.DO.OrderNodeFieldDO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderNodeField;
import top.uhyils.usher.repository.OrderNodeFieldRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 工单节点属性样例表(OrderNodeField)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分24秒
 */
@Repository
public class OrderNodeFieldRepositoryImpl extends AbstractRepository<OrderNodeField, OrderNodeFieldDO, OrderNodeFieldDao, OrderNodeFieldDTO, OrderNodeFieldAssembler> implements OrderNodeFieldRepository {

    protected OrderNodeFieldRepositoryImpl(OrderNodeFieldAssembler convert, OrderNodeFieldDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderNodeField> findByNodeId(Long nodeId) {
        List<OrderNodeFieldDO> byOrderNodeId = dao.getByOrderNodeId(nodeId);
        return assembler.listToEntity(byOrderNodeId);
    }

    @Override
    public List<OrderNodeField> findByNodeIds(List<Long> nodeIds) {
        List<OrderNodeFieldDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds);
        return assembler.listToEntity(byOrderNodeIds);
    }

}
