package top.uhyils.usher.repository.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.TraceInfoAssembler;
import top.uhyils.usher.dao.TraceInfoDao;
import top.uhyils.usher.enums.LogTypeEnum;
import top.uhyils.usher.pojo.DO.TraceDetailStatisticsView;
import top.uhyils.usher.pojo.DO.TraceInfoDO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.cqe.query.demo.Arg;
import top.uhyils.usher.pojo.entity.OnlineMonitors;
import top.uhyils.usher.pojo.entity.Trace;
import top.uhyils.usher.pojo.entity.TraceInfo;
import top.uhyils.usher.redis.RedisPoolHandle;
import top.uhyils.usher.redis.Redisable;
import top.uhyils.usher.repository.TraceInfoRepository;
import top.uhyils.usher.repository.base.AbstractRepository;
import top.uhyils.usher.util.Asserts;
import top.uhyils.usher.util.StringUtil;


/**
 * (TraceInfo)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@Repository
public class TraceInfoRepositoryImpl extends AbstractRepository<TraceInfo, TraceInfoDO, TraceInfoDao, TraceInfoDTO, TraceInfoAssembler> implements TraceInfoRepository {

    /**
     * 服务降级-等级key
     */
    private static final String DEGRADATION_LEVEL = "degradation_level";

    @Autowired
    private RedisPoolHandle redis;

    protected TraceInfoRepositoryImpl(TraceInfoAssembler convert, TraceInfoDao dao) {
        super(convert, dao);
    }

    @Override
    public List<TraceInfo> findTraceInfoByTraceIdAndRpcId(Trace trace) {
        List<TraceInfoDO> traceInfos = dao.getLinkByTraceIdAndRpcIdPrefix(trace.traceId(), trace.rpcId());
        return assembler.listToEntity(traceInfos);
    }

    @Override
    public Long findWebRequestCount(OnlineMonitors logMonitors) {
        return dao.getCountByTypeAndStartTime(LogTypeEnum.CONTROLLER.getCode(), logMonitors.earlyStartTime());
    }

    @Override
    public Long findRpcExecuteCount(OnlineMonitors logMonitors) {
        /* 获取接口调用次数 */
        return dao.getCountByTypeAndStartTime(LogTypeEnum.RPC.getCode(), logMonitors.earlyStartTime());
    }

    @Override
    public List<TraceInfo> findLinkByTraceId(Long traceId) {
        ArrayList<TraceInfoDO> traceInfoByTraceId = dao.getTraceInfoByTraceId(traceId);
        return assembler.listToEntity(traceInfoByTraceId);
    }

    @Override
    public Page<TraceInfo> find(GetTraceInfoByArgAndPageRequest request) {
        List<Arg> args = request.getArgs();
        if (args == null) {
            args = new ArrayList<>();
        }
        Long traceId = request.getTraceId();
        if (traceId != null) {
            args.add(new Arg(TraceInfoDTO::getTraceId, "=", request.getTraceId()));
        }
        Long startTime = request.getStartTime();
        if (startTime != null) {
            args.add(new Arg(TraceInfoDTO::getStartTime, ">", startTime));
        }

        Integer type = request.getType();
        if (type != null) {
            args.add(new Arg(TraceInfoDTO::getLogType, "=", type));
        }
        return find(args, request.getOrder(), request.getLimit());
    }

    @Override
    public Page<TraceDetailStatisticsView> findView(BlackQuery request) {
        List<TraceDetailStatisticsView> traceStatistics = dao.getTraceStatistics(request);
        Long traceStatisticsCount = dao.getTraceStatisticsCount(request);
        return Page.build(traceStatistics, request.getLimit(), traceStatisticsCount);
    }

    @Override
    public Long findConcurrentNumber(Integer logType, Long startTime) {
        return dao.getCountByTypeAndStartTime(logType, startTime);
    }

    @Override
    public Long getRelegationLevel(Long defaultLevel) {
        Asserts.assertTrue(defaultLevel != null);
        try (Redisable jedis = redis.getJedis()) {
            String levelStr = jedis.get(DEGRADATION_LEVEL);
            if (StringUtil.isNotEmpty(levelStr)) {
                return Long.parseLong(levelStr);
            }
            jedis.set(DEGRADATION_LEVEL, defaultLevel.toString());
            return defaultLevel;
        }
    }

}
