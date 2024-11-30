package top.uhyils.usher.protocol.rpc;

import java.util.List;
import top.uhyils.usher.pojo.DTO.TraceDetailStatisticsDTO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import top.uhyils.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import top.uhyils.usher.pojo.DTO.response.LogTypeDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.cqe.query.TraceIdQuery;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年07月31日 06时43分
 */
public interface TraceInfoProvider extends DTOProvider<TraceInfoDTO> {


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

}
