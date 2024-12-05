package top.uhyils.usher.pojo.entity.device.receptor;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.device.core.AbstractLinkDevice;
import top.uhyils.usher.pojo.entity.device.core.Device;
import top.uhyils.usher.pojo.entity.link.Link;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时42分
 */
public abstract class AbstractReceptorDevice extends AbstractLinkDevice implements ReceptorDevice {

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO, Link link) {
        super(obvDeviceMap, deviceDTO, link);
    }

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap, Link link) {
        super(obvDeviceMap, link);
    }

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap, String unique, Link link) {
        super(obvDeviceMap, unique, link);
    }
}
