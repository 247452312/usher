package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderBaseNodeFieldAssembler;
import team.opentech.usher.dao.OrderBaseNodeFieldDao;
import team.opentech.usher.pojo.DO.OrderBaseNodeFieldDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import team.opentech.usher.pojo.entity.OrderBaseNodeField;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.OrderBaseNodeFieldRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 工单节点属性样例表(OrderBaseNodeField)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分01秒
 */
@Repository
public class OrderBaseNodeFieldRepositoryImpl extends AbstractRepository<OrderBaseNodeField, OrderBaseNodeFieldDO, OrderBaseNodeFieldDao, OrderBaseNodeFieldDTO, OrderBaseNodeFieldAssembler> implements OrderBaseNodeFieldRepository {

    protected OrderBaseNodeFieldRepositoryImpl(OrderBaseNodeFieldAssembler convert, OrderBaseNodeFieldDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderBaseNodeField> findNodeFieldByNodes(List<Identifier> nodeIds) {
        List<OrderBaseNodeFieldDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds.stream().map(Identifier::getId).collect(Collectors.toList()));
        return assembler.listToEntity(byOrderNodeIds);
    }
}
