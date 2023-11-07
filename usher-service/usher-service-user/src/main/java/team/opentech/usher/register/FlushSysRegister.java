package team.opentech.usher.register;

import team.opentech.usher.pojo.cqe.FlushAllSysEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.protocol.register.base.Register;
import team.opentech.usher.service.ParamService;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.context.annotation.Lazy;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月17日 16时28分
 */
@team.opentech.usher.annotation.Register
public class FlushSysRegister implements Register {

    @Resource
    @Lazy
    private ParamService service;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Arrays.asList(FlushAllSysEvent.class);
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof FlushAllSysEvent) {
            service.flushAllGlobal();
        }
    }
}
