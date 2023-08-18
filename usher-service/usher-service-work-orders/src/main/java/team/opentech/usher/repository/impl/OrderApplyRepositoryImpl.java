package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderApplyAssembler;
import team.opentech.usher.dao.OrderApplyDao;
import team.opentech.usher.pojo.DO.OrderApplyDO;
import team.opentech.usher.pojo.DTO.OrderApplyDTO;
import team.opentech.usher.pojo.entity.OrderApply;
import team.opentech.usher.repository.OrderApplyRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
