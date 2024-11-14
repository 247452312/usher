package team.opentech.usher.register;


import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.event.CleanSubSpaceEvent;
import team.opentech.usher.protocol.register.base.Register;
import team.opentech.usher.service.AiSubspaceService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时42分
 */
@team.opentech.usher.annotation.Register
public class SubSpaceRegister implements Register {

    @Resource
    private AiSubspaceService service;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Collections.singletonList(CleanSubSpaceEvent.class);
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof CleanSubSpaceEvent) {
            service.cleanSubSpaceEvent((CleanSubSpaceEvent)event);
        }
    }
}
