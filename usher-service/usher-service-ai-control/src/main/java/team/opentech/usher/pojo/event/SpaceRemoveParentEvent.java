package team.opentech.usher.pojo.event;

import java.util.ArrayList;
import java.util.List;
import team.opentech.usher.assembler.AiSpaceAssembler;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.cqe.event.base.AbstractParentEvent;
import team.opentech.usher.pojo.cqe.event.base.BaseEvent;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时37分
 */
public class SpaceRemoveParentEvent extends AbstractParentEvent {

    private AiSpaceDTO aiSpaceDTO;

    public SpaceRemoveParentEvent(AiSpace aiSpace) {
        super();
        AiSpaceAssembler aiSpaceAssembler = SpringUtil.getBean(AiSpaceAssembler.class);
        this.aiSpaceDTO = aiSpaceAssembler.toDTO(aiSpace);
    }

    @Override
    protected List<BaseEvent> transToBaseEvent0() {
        List<BaseEvent> baseEvents = new ArrayList<>();
        // 1. 清空用户
        baseEvents.add(new CleanSpaceUserEvent(aiSpaceDTO.getId()));
        // 2.清空子空间
        baseEvents.add(new CleanSubSpaceEvent(aiSpaceDTO.getId()));

        return baseEvents;
    }
}
