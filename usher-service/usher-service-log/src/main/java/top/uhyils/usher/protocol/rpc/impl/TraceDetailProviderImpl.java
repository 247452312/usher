package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.TraceDetailDTO;
import top.uhyils.usher.pojo.DTO.request.GetTraceDetailByHashCodeRequest;
import top.uhyils.usher.pojo.DTO.response.GetTraceDetailByHashCodeResponse;
import top.uhyils.usher.protocol.rpc.TraceDetailProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.TraceDetailService;

/**
 * (TraceDetail)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
@RpcService
public class TraceDetailProviderImpl extends BaseDefaultProvider<TraceDetailDTO> implements TraceDetailProvider {


    @Autowired
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

