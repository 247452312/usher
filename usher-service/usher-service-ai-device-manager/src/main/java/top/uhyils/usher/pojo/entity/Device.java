package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.pojo.DTO.AiDeviceDTO;

/**
 * 设备本身
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时29分
 */
public interface Device {

    /**
     * 设备的唯一标示
     *
     * @return id
     */
    String id();

    /**
     * 是否是系统控制的设备
     *
     * @return true-是,id是系统id false-否,id是设备传入的唯一id
     */
    boolean aiDevice();

    /**
     * 通过查询到的设备修改当前设备的信息
     *
     * @param deviceDTO
     */
    void changeByDevice(AiDeviceDTO deviceDTO);
}
