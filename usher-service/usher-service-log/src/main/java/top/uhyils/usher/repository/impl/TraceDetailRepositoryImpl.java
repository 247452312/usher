package top.uhyils.usher.repository.impl;

import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.TraceDetailAssembler;
import top.uhyils.usher.dao.TraceDetailDao;
import top.uhyils.usher.pojo.DO.TraceDetailDO;
import top.uhyils.usher.pojo.DTO.TraceDetailDTO;
import top.uhyils.usher.pojo.entity.TraceDetail;
import top.uhyils.usher.pojo.entity.UserSpiderBehavior;
import top.uhyils.usher.repository.TraceDetailRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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
