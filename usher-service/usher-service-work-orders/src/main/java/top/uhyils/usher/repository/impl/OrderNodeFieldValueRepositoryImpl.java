package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderNodeFieldValueAssembler;
import top.uhyils.usher.dao.OrderNodeFieldValueDao;
import top.uhyils.usher.pojo.DO.OrderNodeFieldValueDO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldValueDTO;
import top.uhyils.usher.pojo.entity.OrderNodeFieldValue;
import top.uhyils.usher.repository.OrderNodeFieldValueRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
