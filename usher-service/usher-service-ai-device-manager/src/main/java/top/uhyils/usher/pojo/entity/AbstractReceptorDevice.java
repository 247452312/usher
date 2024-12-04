package top.uhyils.usher.pojo.entity;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时42分
 */
public abstract class AbstractReceptorDevice extends AbstractDevice implements ReceptorDevice {

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO) {
        super(obvDeviceMap, deviceDTO);
    }

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap) {
        super(obvDeviceMap);
    }

    public AbstractReceptorDevice(Map<String, Device> obvDeviceMap, String unique) {
        super(obvDeviceMap, unique);
    }
}
