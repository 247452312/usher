package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.OrderBaseInfoAssembler;
import team.opentech.usher.dao.OrderBaseInfoDao;
import team.opentech.usher.pojo.DO.OrderBaseInfoDO;
import team.opentech.usher.pojo.DTO.OrderBaseInfoDTO;
import team.opentech.usher.pojo.entity.OrderBaseInfo;
import team.opentech.usher.repository.OrderBaseInfoRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.ArrayList;
import java.util.List;


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
