package top.uhyils.usher.pojo.event;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.assembler.AiDeviceAssembler;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.event.base.AbstractParentEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.entity.AiDevice;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时06分
 */
public class DeviceRemoveParentEvent extends AbstractParentEvent {

    private AiDeviceDTO deviceDTO;

    public DeviceRemoveParentEvent(AiDevice aiDevice) {
        this.deviceDTO = SpringUtil.getBean(AiDeviceAssembler.class).toDTO(aiDevice);
    }

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        List<BaseEvent> baseEvents = new ArrayList<>();
        // 1.清空相关指令
        baseEvents.add(new DeviceInstructionCleanEvent(deviceDTO.getId()));
        // 2.清空实时状态
        baseEvents.add(new DeviceRealTimeCleanEvent(deviceDTO.getId()));
        return baseEvents;
    }
}
