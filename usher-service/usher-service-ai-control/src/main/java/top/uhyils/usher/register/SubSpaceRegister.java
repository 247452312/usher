package top.uhyils.usher.register;


import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.event.CleanSubSpaceEvent;
import top.uhyils.usher.protocol.register.base.Register;
import top.uhyils.usher.service.AiSubspaceService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时42分
 */
@top.uhyils.usher.annotation.Register
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
