package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.TraceDetailDTO;
import top.uhyils.usher.pojo.DTO.request.GetTraceDetailByHashCodeRequest;
import top.uhyils.usher.pojo.DTO.response.GetTraceDetailByHashCodeResponse;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月02日 08时14分
 */
public interface TraceDetailProvider extends DTOProvider<TraceDetailDTO> {

    /**
     * 获取链路详情
     *
     * @param request
     *
     * @return
     */
    GetTraceDetailByHashCodeResponse getTraceDetailByHashCode(GetTraceDetailByHashCodeRequest request);


}
