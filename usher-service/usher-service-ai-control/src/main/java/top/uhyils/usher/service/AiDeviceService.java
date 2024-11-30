package top.uhyils.usher.service;


import java.util.List;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.ChangeDeviceCommand;
import top.uhyils.usher.pojo.cqe.ChangePositionCommand;
import top.uhyils.usher.pojo.cqe.CreateDeviceCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.event.DeviceCleanEvent;
import top.uhyils.usher.pojo.event.DeviceInstructionCleanEvent;

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

    /**
     * 根据子空间id获取设备id
     *
     * @param query
     *
     * @return
     */
    List<AiDeviceDTO> findDeviceBySubSpaceId(IdQuery query);


    /**
     * 创建新设备
     *
     * @param command
     *
     * @return
     */
    Boolean createDevice(CreateDeviceCommand command);

    /**
     * 删除设备
     *
     * @param command
     *
     * @return
     */
    Boolean removeDevice(IdCommand command);

    /**
     * 批量删除设备
     *
     * @param command
     *
     * @return
     */
    Boolean removeDevices(IdsCommand command);

    /**
     * 根据子空间删除设备
     *
     * @param command
     *
     * @return
     */
    Boolean removeDeviceBySubSpaceId(IdCommand command);


    /**
     * 修改设备位置(某个设备换位置了)
     *
     * @param command
     *
     * @return
     */
    Boolean changePosition(ChangePositionCommand command);


    /**
     * 修改设备名称
     *
     * @param command
     *
     * @return
     */
    Boolean changeDevice(ChangeDeviceCommand command);


    /**
     * 根据独立空间获取所有设备
     *
     * @param query
     *
     * @return
     */
    List<AiDeviceDTO> findDeviceBySpaceId(IdQuery query);


}
