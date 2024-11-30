package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.TraceDetailStatisticsDTO;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetLinkByTraceIdAndRpcIdQuery;
import top.uhyils.usher.pojo.DTO.request.GetTraceInfoByArgAndPageRequest;
import top.uhyils.usher.pojo.DTO.response.LogTypeDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.BlackQuery;
import top.uhyils.usher.pojo.cqe.query.TraceIdQuery;
import top.uhyils.usher.protocol.rpc.TraceInfoProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.TraceInfoService;

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

