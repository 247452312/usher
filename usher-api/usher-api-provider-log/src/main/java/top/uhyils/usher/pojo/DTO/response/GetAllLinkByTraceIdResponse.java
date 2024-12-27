package top.uhyils.usher.pojo.DTO.response;

import java.io.Serializable;
import java.util.List;
import top.uhyils.usher.pojo.DTO.TraceInfoDTO;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年07月31日 21时04分
 */
public class GetAllLinkByTraceIdResponse implements Serializable {

    /**
     * trace信息
     */
    private List<TraceInfoDTO> traceInfos;

    public static GetAllLinkByTraceIdResponse build(List<TraceInfoDTO> traceInfos) {
        GetAllLinkByTraceIdResponse build = new GetAllLinkByTraceIdResponse();
        build.traceInfos = traceInfos;
        return build;
    }

    public List<TraceInfoDTO> getTraceInfos() {
        return traceInfos;
    }

    public void setTraceInfos(List<TraceInfoDTO> traceInfos) {
        this.traceInfos = traceInfos;
    }
}
