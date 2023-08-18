package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderApiAssembler;
import team.opentech.usher.dao.OrderApiDao;
import team.opentech.usher.pojo.DO.OrderApiDO;
import team.opentech.usher.pojo.DTO.OrderApiDTO;
import team.opentech.usher.pojo.entity.OrderApi;
import team.opentech.usher.repository.OrderApiRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 节点api表(OrderApi)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分46秒
 */
@Repository
public class OrderApiRepositoryImpl extends AbstractRepository<OrderApi, OrderApiDO, OrderApiDao, OrderApiDTO, OrderApiAssembler> implements OrderApiRepository {

    protected OrderApiRepositoryImpl(OrderApiAssembler convert, OrderApiDao dao) {
        super(convert, dao);
    }


}
