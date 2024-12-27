package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderInfoAssembler;
import top.uhyils.usher.dao.OrderInfoDao;
import top.uhyils.usher.pojo.DO.OrderInfoDO;
import top.uhyils.usher.pojo.DTO.OrderInfoDTO;
import top.uhyils.usher.pojo.entity.OrderInfo;
import top.uhyils.usher.repository.OrderInfoRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 工单基础信息样例表(OrderInfo)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分14秒
 */
@Repository
public class OrderInfoRepositoryImpl extends AbstractRepository<OrderInfo, OrderInfoDO, OrderInfoDao, OrderInfoDTO, OrderInfoAssembler> implements OrderInfoRepository {

    protected OrderInfoRepositoryImpl(OrderInfoAssembler convert, OrderInfoDao dao) {
        super(convert, dao);
    }


    @Override
    public List<OrderInfo> findByType(Integer type) {
        List<OrderInfoDO> orderByType = dao.getOrderByType(type);
        return assembler.listToEntity(orderByType);
    }
}
