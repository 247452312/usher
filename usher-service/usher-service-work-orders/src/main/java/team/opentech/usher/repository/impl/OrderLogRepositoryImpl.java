package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderLogAssembler;
import team.opentech.usher.dao.OrderLogDao;
import team.opentech.usher.pojo.DO.OrderLogDO;
import team.opentech.usher.pojo.DTO.OrderLogDTO;
import team.opentech.usher.pojo.entity.OrderLog;
import team.opentech.usher.repository.OrderLogRepository;
import team.opentech.usher.repository.base.AbstractRepository;


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
