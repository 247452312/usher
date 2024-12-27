package top.uhyils.usher.pojo.DTO.response;

import java.io.Serializable;
import top.uhyils.usher.pojo.DTO.TraceDetailDTO;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月04日 09时10分
 */
public class GetTraceDetailByHashCodeResponse implements Serializable {

    /**
     * trace
     */
    private TraceDetailDTO traceDetailEntity;

    public static GetTraceDetailByHashCodeResponse build(TraceDetailDTO traceDetailEntity) {
        GetTraceDetailByHashCodeResponse build = new GetTraceDetailByHashCodeResponse();
        build.traceDetailEntity = traceDetailEntity;
        return build;
    }

    public TraceDetailDTO getTraceDetailEntity() {
        return traceDetailEntity;
    }

    public void setTraceDetailEntity(TraceDetailDTO traceDetailEntity) {
        this.traceDetailEntity = traceDetailEntity;
    }
}
