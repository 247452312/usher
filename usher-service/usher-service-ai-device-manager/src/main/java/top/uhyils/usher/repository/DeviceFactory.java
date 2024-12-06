package top.uhyils.usher.repository;

import top.uhyils.usher.pojo.entity.device.core.Device;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时58分
 */
public interface DeviceFactory {

    /**
     * 获取设备,如果没有,就去根据控制中心获取一个
     */
    Device getDevice(String uniqueMark);
}
