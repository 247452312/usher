package team.opentech.usher.protocol.register.impl;

import team.opentech.usher.enums.LogTypeEnum;
import team.opentech.usher.pojo.DTO.TraceDetailDTO;
import team.opentech.usher.pojo.cqe.event.CheckAndAddRelegationEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.protocol.register.RelegationRegister;
import team.opentech.usher.service.RelegationService;
import team.opentech.usher.util.Asserts;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时07分
 */
@team.opentech.usher.annotation.Register
public class RelegationRegisterImpl implements RelegationRegister {

    @Autowired
    private RelegationService service;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Arrays.asList(
            CheckAndAddRelegationEvent.class
        );
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof CheckAndAddRelegationEvent) {
            CheckAndAddRelegationEvent checkAndAddRelegationEvent = (CheckAndAddRelegationEvent) event;
            this.checkAndAddRelegation(checkAndAddRelegationEvent);

        }
    }

    @Override
    public void checkAndAddRelegation(CheckAndAddRelegationEvent event) {
        TraceDetailDTO traceDetailDTO = event.getTraceDetailDTO();
        if (traceDetailDTO != null) {
            Integer type = traceDetailDTO.getType();
            LogTypeEnum parse = LogTypeEnum.parse(type).orElseThrow(() -> Asserts.makeException("降级监听: 未找到日志链路类型"));
            if (parse != LogTypeEnum.RPC) {
                return;
            }
        }

        service.checkAndAddRelegation(event);
    }
}
