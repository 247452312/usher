package top.uhyils.usher.pojo.event;

import top.uhyils.usher.pojo.cqe.event.base.AbstractEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 16时38分
 */
public class CleanSpaceUserEvent extends AbstractEvent {

    /**
     * 空间id
     */
    private Long spaceId;

    public CleanSpaceUserEvent() {
    }

    public CleanSpaceUserEvent(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

}
