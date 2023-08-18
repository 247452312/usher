package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.TraceDetailStatisticsDTO;
import team.opentech.usher.pojo.DTO.TraceInfoDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import team.opentech.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import team.opentech.usher.pojo.DTO.response.LogTypeDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.query.BlackQuery;
import team.opentech.usher.pojo.cqe.query.TraceIdQuery;
import team.opentech.usher.protocol.rpc.TraceInfoProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.TraceInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * (TraceInfo)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@RpcService
public class TraceInfoProviderImpl extends BaseDefaultProvider<TraceInfoDTO> implements TraceInfoProvider {


    @Autowired
    private TraceInfoService service;

    @Override
    public List<TraceInfoDTO> getLinkByTraceIdAndRpcId(GetLinkByTraceIdAndRpcIdQuery request) {
        return service.getLinkByTraceIdAndRpcId(request);
    }

    @Override
    public List<TraceInfoDTO> getLinkByTraceId(TraceIdQuery request) {
        return service.getLinkByTraceId(request);
    }

    @Override
    public Page<TraceInfoDTO> getTraceInfoByArgAndPage(GetTraceInfoByArgAndPageRequest request) {
        return service.getTraceInfoByArgAndPage(request);
    }

    @Override
    public Page<TraceDetailStatisticsDTO> getTraceStatistics(BlackQuery request) {
        return service.getTraceStatistics(request);
    }

    @Override
    public List<LogTypeDTO> getLogType(DefaultCQE request) {
        return service.getLogType(request);
    }

    @Override
    protected BaseDoService<TraceInfoDTO> getService() {
        return service;
    }
}

