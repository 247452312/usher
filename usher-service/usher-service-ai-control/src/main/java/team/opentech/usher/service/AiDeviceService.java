package team.opentech.usher.service;


import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.event.DeviceCleanEvent;
import team.opentech.usher.pojo.event.DeviceInstructionCleanEvent;

/**
 * 设备表(AiDevice)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceService extends BaseDoService<AiDeviceDTO> {

    /**
     * 设备清空事件
     *
     * @param event
     */
    void deviceCleanEvent(DeviceCleanEvent event);

    /**
     * 设备指令清空事件
     *
     * @param event
     */
    void deviceInstructionCleanEvent(DeviceInstructionCleanEvent event);
}
