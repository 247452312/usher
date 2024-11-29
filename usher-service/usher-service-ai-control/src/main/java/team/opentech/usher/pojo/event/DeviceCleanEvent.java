package team.opentech.usher.pojo.event;

import team.opentech.usher.pojo.cqe.event.base.AbstractEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时02分
 */
public class DeviceCleanEvent extends AbstractEvent {

    private Long subspaceId;

    public DeviceCleanEvent(Long subspaceId) {
        this.subspaceId = subspaceId;
    }

    public DeviceCleanEvent() {
    }

    public Long getSubspaceId() {
        return subspaceId;
    }

    public void setSubspaceId(Long subspaceId) {
        this.subspaceId = subspaceId;
    }
}
