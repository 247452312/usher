package top.uhyils.usher.pojo.entity.device.core;

import top.uhyils.usher.pojo.DTO.AiDeviceDTO;

/**
 * 设备抽象类,包含了设备本身的功能
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
    String uniqueMark();

    /**
     * 是否是系统控制的设备
     *
     * @return true-是,id是系统id false-否,id是设备传入的唯一id
     */
    boolean isAiDevice();

    /**
     * 通过查询到的设备修改当前设备的信息
     *
     * @param deviceDTO
     */
    void changeByDevice(AiDeviceDTO deviceDTO);

    /**
     * 停止连接
     */
    void stop();

    /**
     * 开始连接
     */
    void start();

    /**
     * 发送请求,异步
     *
     * @param request
     */
    void request(String request);

    /**
     * 发送请求,同步
     *
     * @param request
     */
    String requestSync(String request);

    /**
     * 获取设备, 如果isAiDevice() 返回为false,则此方法返回null
     *
     * @return
     */
    AiDeviceDTO aiDevice();

    /**
     * 设备所在的ip
     *
     * @return 设备所在的ip, 网络设备则返回对应的网络ip
     */
    String ip();
}
