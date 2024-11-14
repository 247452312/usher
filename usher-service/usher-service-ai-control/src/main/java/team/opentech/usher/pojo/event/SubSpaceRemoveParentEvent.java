package team.opentech.usher.pojo.event;

import java.util.ArrayList;
import java.util.List;
import team.opentech.usher.assembler.AiSubspaceAssembler;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.cqe.event.base.AbstractParentEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时59分
 */
public class SubSpaceRemoveParentEvent extends AbstractParentEvent {

    private final AiSubspaceDTO aiSubspaceDTO;

    public SubSpaceRemoveParentEvent(AiSubspace aiSubspace) {
        super();
        this.aiSubspaceDTO = SpringUtil.getBean(AiSubspaceAssembler.class).toDTO(aiSubspace);
    }

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        List<BaseEvent> baseEvents = new ArrayList<>();
        // 1.清空设备
        baseEvents.add(new DeviceCleanEvent(aiSubspaceDTO.getId()));
        return baseEvents;
    }
}
