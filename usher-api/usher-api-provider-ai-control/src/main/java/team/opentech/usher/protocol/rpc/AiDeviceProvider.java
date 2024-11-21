package team.opentech.usher.protocol.rpc;

import java.util.List;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.ChangeDeviceCommand;
import team.opentech.usher.pojo.cqe.ChangePositionCommand;
import team.opentech.usher.pojo.cqe.CreateDeviceCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.base.DTOProvider;

/**
 * 设备表(AiDevice)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceProvider extends DTOProvider<AiDeviceDTO> {


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
     * 根据子空间id获取所有设备
     *
     * @param query
     *
     * @return
     */
    List<AiDeviceDTO> findDeviceBySubSpaceId(IdQuery query);

    /**
     * 根据独立空间获取所有设备
     *
     * @param query
     *
     * @return
     */
    List<AiDeviceDTO> findDeviceBySpaceId(IdQuery query);

}
