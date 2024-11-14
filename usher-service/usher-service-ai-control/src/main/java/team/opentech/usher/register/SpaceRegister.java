package team.opentech.usher.register;


import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.event.CleanSpaceUserEvent;
import team.opentech.usher.protocol.register.base.Register;
import team.opentech.usher.service.AiSpaceService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时42分
 */
@team.opentech.usher.annotation.Register
public class SpaceRegister implements Register {

    @Resource
    private AiSpaceService spaceService;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Collections.singletonList(CleanSpaceUserEvent.class);
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof CleanSpaceUserEvent) {
            spaceService.cleanSpaceUserEvent((CleanSpaceUserEvent)event);
        }
    }
}
