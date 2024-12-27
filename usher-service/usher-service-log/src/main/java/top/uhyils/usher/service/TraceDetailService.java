package top.uhyils.usher.service;


import top.uhyils.usher.pojo.DTO.TraceDetailDTO;
import top.uhyils.usher.pojo.DTO.request.GetTraceDetailByHashCodeRequest;
import top.uhyils.usher.pojo.DTO.response.GetTraceDetailByHashCodeResponse;

/**
 * (TraceDetail)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分54秒
 */
public interface TraceDetailService extends BaseDoService<TraceDetailDTO> {

    /**
     * 获取链路详情
     *
     * @param request
     *
     * @return
     */
    GetTraceDetailByHashCodeResponse getTraceDetailByHashCode(GetTraceDetailByHashCodeRequest request);

}
