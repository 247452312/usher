package team.opentech.usher.repository;

import team.opentech.usher.pojo.DO.TraceDetailStatisticsView;
import team.opentech.usher.pojo.DO.TraceInfoDO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.pojo.entity.OnlineMonitors;
import team.opentech.usher.pojo.entity.Trace;
import team.opentech.usher.pojo.entity.TraceInfo;
import team.opentech.usher.repository.base.BaseEntityRepository;
import java.util.List;

/**
 * (TraceInfo)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
public interface TraceInfoRepository extends BaseEntityRepository<TraceInfoDO, TraceInfo> {


    /**
     * 根据traceId和rpcId获取链路信息
     *
     * @param trace
     *
     * @return
     */
    List<TraceInfo> findTraceInfoByTraceIdAndRpcId(Trace trace);

    /**
     * 获取前台请求次数
     *
     * @param logMonitors
     *
     * @return
     */
    Long findWebRequestCount(OnlineMonitors logMonitors);

    /**
     * 获取rpc总次数
     *
     * @param logMonitors
     *
     * @return
     */
    Long findRpcExecuteCount(OnlineMonitors logMonitors);

    /**
     * 根据traceId获取link
     *
     * @param traceId
     *
     * @return
     */
    List<TraceInfo> findLinkByTraceId(Long traceId);

    /**
     * 根据query获取
     *
     * @param request
     *
     * @return
     */
    Page<TraceInfo> find(GetTraceInfoByArgAndPageRequest request);

    /**
     * 视图获取
     *
     * @param request
     *
     * @return
     */
    Page<TraceDetailStatisticsView> findView(BlackQuery request);


    /**
     * 获取每秒网关并发数
     *
     * @return
     */
    Long findConcurrentNumber(Integer logType, Long startTime);

    /**
     * 获取服务降级当前等级
     *
     * @param defaultLevel
     *
     * @return
     */
    Long getRelegationLevel(Long defaultLevel);

}
