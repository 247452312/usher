package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.TraceDetailStatisticsDTO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import top.uhyils.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import top.uhyils.usher.pojo.DTO.response.LogTypeDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.BlankCommand;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.cqe.query.TraceIdQuery;

/**
 * (TraceInfo)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
public interface TraceInfoService extends BaseDoService<TraceInfoDTO> {

    /**
     * 根据traceId和rpcId获取这一串
     *
     * @param request
     *
     * @return
     */
    List<TraceInfoDTO> getLinkByTraceIdAndRpcId(GetLinkByTraceIdAndRpcIdQuery request);

    /**
     * 根据traceId获取这一串
     *
     * @param request
     *
     * @return
     */
    List<TraceInfoDTO> getLinkByTraceId(TraceIdQuery request);


    /**
     * 获取traceInfo
     *
     * @param request
     *
     * @return
     */
    Page<TraceInfoDTO> getTraceInfoByArgAndPage(GetTraceInfoByArgAndPageRequest request);

    /**
     * 获取日志归档信息
     *
     * @param request
     *
     * @return
     */
    Page<TraceDetailStatisticsDTO> getTraceStatistics(BlackQuery request);

    /**
     * 获取日志类型
     *
     * @param request
     *
     * @return
     */
    List<LogTypeDTO> getLogType(DefaultCQE request);

    /**
     * 监控并发数
     *
     * @param request
     */
    void monitorConcurrentNumber(BlankCommand request);

    /**
     * 获取每秒网关并发数
     *
     * @param request
     *
     * @return
     */
    Long getConcurrentNumber(DefaultCQE request);
}
