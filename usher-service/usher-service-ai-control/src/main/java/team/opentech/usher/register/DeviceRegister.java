package team.opentech.usher.register;


import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.event.DeviceCleanEvent;
import team.opentech.usher.pojo.event.DeviceInstructionCleanEvent;
import team.opentech.usher.protocol.register.base.Register;
import team.opentech.usher.service.AiDeviceService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时42分
 */
@team.opentech.usher.annotation.Register
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
