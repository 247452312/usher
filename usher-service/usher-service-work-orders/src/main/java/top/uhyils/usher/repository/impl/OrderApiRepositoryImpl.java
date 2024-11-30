package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderApiAssembler;
import top.uhyils.usher.dao.OrderApiDao;
import top.uhyils.usher.pojo.DO.OrderApiDO;
import top.uhyils.usher.pojo.DTO.OrderApiDTO;
import top.uhyils.usher.pojo.entity.OrderApi;
import top.uhyils.usher.repository.OrderApiRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
