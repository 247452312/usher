package top.uhyils.usher.register;


import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.event.DeviceCleanEvent;
import top.uhyils.usher.pojo.event.DeviceInstructionCleanEvent;
import top.uhyils.usher.protocol.register.base.Register;
import top.uhyils.usher.service.AiDeviceService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时42分
 */
@top.uhyils.usher.annotation.Register
public class DeviceRegister implements Register {

    @Resource
    private AiDeviceService service;

    @Override
    public List<Class<? extends BaseEvent>> targetEvent() {
        return Arrays.asList(DeviceCleanEvent.class, DeviceInstructionCleanEvent.class);
    }

    @Override
    public void onEvent(BaseEvent event) {
        if (event instanceof DeviceCleanEvent) {
            service.deviceCleanEvent((DeviceCleanEvent) event);
        } else if (event instanceof DeviceInstructionCleanEvent) {
            service.deviceInstructionCleanEvent((DeviceInstructionCleanEvent) event);
        }
    }
}
