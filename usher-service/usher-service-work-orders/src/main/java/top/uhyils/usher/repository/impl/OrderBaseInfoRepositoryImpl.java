package top.uhyils.usher.repository.impl;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.OrderBaseInfoAssembler;
import top.uhyils.usher.dao.OrderBaseInfoDao;
import top.uhyils.usher.pojo.DO.OrderBaseInfoDO;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.entity.OrderBaseInfo;
import top.uhyils.usher.repository.OrderBaseInfoRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 工单基础信息样例表(OrderBaseInfo)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分54秒
 */
@Repository
public class OrderBaseInfoRepositoryImpl extends AbstractRepository<OrderBaseInfo, OrderBaseInfoDO, OrderBaseInfoDao, OrderBaseInfoDTO, OrderBaseInfoAssembler> implements OrderBaseInfoRepository {

    protected OrderBaseInfoRepositoryImpl(OrderBaseInfoAssembler convert, OrderBaseInfoDao dao) {
        super(convert, dao);
    }

    @Override
    public List<OrderBaseInfoDTO> getAllBaseOrderIdAndName() {
        ArrayList<OrderBaseInfoDO> allBaseOrderIdAndName = dao.getAllBaseOrderIdAndName();
        return assembler.listDoToDTO(allBaseOrderIdAndName);
    }
}
