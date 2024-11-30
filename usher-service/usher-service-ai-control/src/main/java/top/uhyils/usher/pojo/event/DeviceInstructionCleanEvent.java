package top.uhyils.usher.pojo.event;

import top.uhyils.usher.pojo.cqe.event.base.AbstractEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时08分
 */
public class DeviceInstructionCleanEvent extends AbstractEvent {

    /**
     * 设备id
     */
    private Long deviceId;

    public DeviceInstructionCleanEvent(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
