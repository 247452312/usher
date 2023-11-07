package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.DTO.request.GetTraceDetailByHashCodeRequest;
import team.opentech.usher.pojo.DTO.response.GetTraceDetailByHashCodeResponse;
import team.opentech.usher.protocol.rpc.TraceDetailProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.TraceDetailService;
import javax.annotation.Resource;

/**
 * (TraceDetail)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
@RpcService
public class TraceDetailProviderImpl extends BaseDefaultProvider<TraceDetailDTO> implements TraceDetailProvider {


    @Resource
    private TraceDetailService service;

    @Override
    public GetTraceDetailByHashCodeResponse getTraceDetailByHashCode(GetTraceDetailByHashCodeRequest request) {
        return null;
    }

    @Override
    protected BaseDoService<TraceDetailDTO> getService() {
        return service;
    }
}

