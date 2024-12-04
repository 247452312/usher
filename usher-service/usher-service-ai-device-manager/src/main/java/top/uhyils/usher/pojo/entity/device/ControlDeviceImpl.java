package top.uhyils.usher.pojo.entity.device;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;

/**
 * 传感器
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月04日 19时39分
 */
public class ControlDeviceImpl extends AbstractControlDevice implements ControlDevice {


    public ControlDeviceImpl(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO) {
        super(obvDeviceMap, deviceDTO);
    }

    public ControlDeviceImpl(Map<String, Device> obvDeviceMap) {
        super(obvDeviceMap);
    }

    public ControlDeviceImpl(Map<String, Device> obvDeviceMap, String unique) {
        super(obvDeviceMap, unique);
    }

    @Override
    public void start() {

    }


    @Override
    public Object control(Object param) {
        return true;
    }
}
