package top.uhyils.usher.repository.impl;

import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderLogAssembler;
import top.uhyils.usher.dao.OrderLogDao;
import top.uhyils.usher.pojo.DO.OrderLogDO;
import top.uhyils.usher.pojo.DTO.OrderLogDTO;
import top.uhyils.usher.pojo.entity.OrderLog;
import top.uhyils.usher.repository.OrderLogRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 日志表(OrderLog)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分17秒
 */
@Repository
public class OrderLogRepositoryImpl extends AbstractRepository<OrderLog, OrderLogDO, OrderLogDao, OrderLogDTO, OrderLogAssembler> implements OrderLogRepository {

    protected OrderLogRepositoryImpl(OrderLogAssembler convert, OrderLogDao dao) {
        super(convert, dao);
    }


}
