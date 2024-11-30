package top.uhyils.usher.repository.impl;

import java.util.List;
import java.util.stream.Collectors;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderBaseNodeFieldAssembler;
import top.uhyils.usher.dao.OrderBaseNodeFieldDao;
import top.uhyils.usher.pojo.DO.OrderBaseNodeFieldDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeField;
import top.uhyils.usher.repository.OrderBaseNodeFieldRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
    public List<OrderBaseNodeField> findNodeFieldByNodes(List<Long> nodeIds) {
        List<OrderBaseNodeFieldDO> byOrderNodeIds = dao.getByOrderNodeIds(nodeIds.stream().collect(Collectors.toList()));
        return assembler.listToEntity(byOrderNodeIds);
    }
}
