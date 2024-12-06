package top.uhyils.usher.pojo.entity.device.demo;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.device.core.AbstractLinkDevice;
import top.uhyils.usher.pojo.entity.device.core.Device;
import top.uhyils.usher.pojo.entity.link.Link;

/**
 * 简单感受器,可以作为传感器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月05日 08时54分
 */
public class SingleDevice extends AbstractLinkDevice {

    public SingleDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO, Link link) {
        super(obvDeviceMap, deviceDTO, link);
    }

    public SingleDevice(Map<String, Device> obvDeviceMap, Link link) {
        super(obvDeviceMap, link);
    }

    public SingleDevice(Map<String, Device> obvDeviceMap, String unique, Link link) {
        super(obvDeviceMap, unique, link);
    }

    public static SingleDevice buildAiDeviceSingleDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO, Link link) {
        return new SingleDevice(obvDeviceMap, deviceDTO, link);
    }

    public static SingleDevice buildNoUniqueSingleDevice(Map<String, Device> obvDeviceMap, Link link) {
        return new SingleDevice(obvDeviceMap, link);
    }

    public static SingleDevice buildUniqueSingleDevice(Map<String, Device> obvDeviceMap, String unique, Link link) {
        return new SingleDevice(obvDeviceMap, unique, link);
    }

    @Override
    protected void onMessage(String msg) {

    }
}
