package top.uhyils.usher.pojo.event;

import java.util.ArrayList;
import java.util.List;
import top.uhyils.usher.assembler.AiSubspaceAssembler;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.cqe.event.base.AbstractParentEvent;
import top.uhyils.usher.pojo.cqe.event.base.BaseEvent;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.util.SpringUtil;

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
