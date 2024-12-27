package top.uhyils.usher.register;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import top.uhyils.usher.pojo.cqe.FlushAllSysEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.protocol.register.base.Register;
import top.uhyils.usher.service.ParamService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2022年06月17日 16时28分
 */
@top.uhyils.usher.annotation.Register
public class FlushSysRegister implements Register {

    @Autowired
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
