package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.TraceDetailDO;
import team.opentech.usher.pojo.entity.TraceDetail;
import team.opentech.usher.pojo.entity.UserSpiderBehavior;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * (TraceDetail)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
public interface TraceDetailRepository extends BaseEntityRepository<TraceDetailDO, TraceDetail> {


    /**
     * 获取最后n次执行的时间点
     *
     * @param userBehavior
     *
     * @return
     */
    List<Long> findLastTime(UserSpiderBehavior userBehavior);
}
