package team.opentech.usher.protocol.rpc;

import team.opentech.usher.pojo.DTO.TraceDetailStatisticsDTO;
import team.opentech.usher.pojo.DTO.TraceInfoDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import team.opentech.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import team.opentech.usher.pojo.DTO.response.LogTypeDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.pojo.cqe.query.TraceIdQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;
import java.util.List;

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
