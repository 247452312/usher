package team.opentech.usher.pojo.event;

import java.util.ArrayList;
import java.util.List;
import team.opentech.usher.assembler.AiDeviceAssembler;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.event.base.AbstractParentEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.util.SpringUtil;

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
        return baseEvents;
    }
}
