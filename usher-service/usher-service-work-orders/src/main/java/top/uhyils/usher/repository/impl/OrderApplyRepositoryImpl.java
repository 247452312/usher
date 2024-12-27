package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderApplyAssembler;
import top.uhyils.usher.dao.OrderApplyDao;
import top.uhyils.usher.pojo.DO.OrderApplyDO;
import top.uhyils.usher.pojo.DTO.OrderApplyDTO;
import top.uhyils.usher.pojo.entity.OrderApply;
import top.uhyils.usher.repository.OrderApplyRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * (OrderApply)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分50秒
 */
@Repository
public class OrderApplyRepositoryImpl extends AbstractRepository<OrderApply, OrderApplyDO, OrderApplyDao, OrderApplyDTO, OrderApplyAssembler> implements OrderApplyRepository {

    protected OrderApplyRepositoryImpl(OrderApplyAssembler convert, OrderApplyDao dao) {
        super(convert, dao);
    }


}
