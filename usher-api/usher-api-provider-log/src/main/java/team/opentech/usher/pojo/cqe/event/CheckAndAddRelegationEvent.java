package team.opentech.usher.pojo.cqe.event;

import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.cqe.event.base.AbstractEvent;
import javax.validation.constraints.NotNull;


/**
 * 检查并插入接口降级表
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 08时55分
 */
public class CheckAndAddRelegationEvent extends AbstractEvent {

    @NotNull
    private final TraceDetailDTO traceDetailDTO;

    public CheckAndAddRelegationEvent(TraceDetailDTO traceDetailDTO) {
        this.traceDetailDTO = traceDetailDTO;
    }

    public TraceDetailDTO getTraceDetailDTO() {
        return traceDetailDTO;
    }
}
