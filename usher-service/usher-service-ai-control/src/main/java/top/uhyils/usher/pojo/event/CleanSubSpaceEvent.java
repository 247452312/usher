package top.uhyils.usher.pojo.event;

import top.uhyils.usher.pojo.cqe.event.base.AbstractEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时48分
 */
public class CleanSubSpaceEvent extends AbstractEvent {

    /**
     * 独立空间id
     */
    private Long spaceId;

    public CleanSubSpaceEvent() {
    }

    public CleanSubSpaceEvent(Long spaceId) {
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }
}
