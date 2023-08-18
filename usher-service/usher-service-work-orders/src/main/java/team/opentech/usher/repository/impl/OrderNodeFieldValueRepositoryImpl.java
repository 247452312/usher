package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderNodeFieldValueAssembler;
import team.opentech.usher.dao.OrderNodeFieldValueDao;
import team.opentech.usher.pojo.DO.OrderNodeFieldValueDO;
import team.opentech.usher.pojo.DTO.OrderNodeFieldValueDTO;
import team.opentech.usher.pojo.entity.OrderNodeFieldValue;
import team.opentech.usher.repository.OrderNodeFieldValueRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 订单节点属性真实值表(OrderNodeFieldValue)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分27秒
 */
@Repository
public class OrderNodeFieldValueRepositoryImpl extends AbstractRepository<OrderNodeFieldValue, OrderNodeFieldValueDO, OrderNodeFieldValueDao, OrderNodeFieldValueDTO, OrderNodeFieldValueAssembler> implements OrderNodeFieldValueRepository {

    protected OrderNodeFieldValueRepositoryImpl(OrderNodeFieldValueAssembler convert, OrderNodeFieldValueDao dao) {
        super(convert, dao);
    }


}
