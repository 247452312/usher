package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.TraceDetailDO;
import top.uhyils.usher.pojo.entity.TraceDetail;
import top.uhyils.usher.pojo.entity.UserSpiderBehavior;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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
