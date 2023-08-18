package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.TraceDetailAssembler;
import team.opentech.usher.dao.TraceDetailDao;
import team.opentech.usher.pojo.DO.TraceDetailDO;
import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.entity.TraceDetail;
import team.opentech.usher.pojo.entity.UserSpiderBehavior;
import team.opentech.usher.repository.TraceDetailRepository;
import team.opentech.usher.repository.base.AbstractRepository;
import java.util.List;


/**
 * (TraceDetail)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
@Repository
public class TraceDetailRepositoryImpl extends AbstractRepository<TraceDetail, TraceDetailDO, TraceDetailDao, TraceDetailDTO, TraceDetailAssembler> implements TraceDetailRepository {

    protected TraceDetailRepositoryImpl(TraceDetailAssembler convert, TraceDetailDao dao) {
        super(convert, dao);
    }


    @Override
    public List<Long> findLastTime(UserSpiderBehavior userBehavior) {
        return dao.getTimeByIp(userBehavior.ip(), userBehavior.size());
    }
}
