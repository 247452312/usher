package top.uhyils.usher.pojo.event;

import top.uhyils.usher.pojo.cqe.event.base.AbstractEvent;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 17时21分
 */
public class DeviceRealTimeCleanEvent extends AbstractEvent {

    /**
     * 设备id
     */
    private Long deviceId;

    public DeviceRealTimeCleanEvent(Long deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceRealTimeCleanEvent() {
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }
}
