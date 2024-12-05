package top.uhyils.usher.pojo.entity.device.control;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.device.core.AbstractDevice;
import top.uhyils.usher.pojo.entity.device.core.Device;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时43分
 */
public abstract class AbstractControlDevice extends AbstractDevice implements ControlDevice {

    public AbstractControlDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO) {
        super(obvDeviceMap, deviceDTO);
    }

    public AbstractControlDevice(Map<String, Device> obvDeviceMap) {
        super(obvDeviceMap);
    }

    public AbstractControlDevice(Map<String, Device> obvDeviceMap, String unique) {
        super(obvDeviceMap, unique);
    }
}
