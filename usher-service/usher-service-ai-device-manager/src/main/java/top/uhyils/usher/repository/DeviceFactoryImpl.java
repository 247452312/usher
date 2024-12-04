package top.uhyils.usher.repository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.enums.AiDeviceStatusEnum;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.device.ControlDeviceImpl;
import top.uhyils.usher.pojo.entity.device.Device;
import top.uhyils.usher.pojo.entity.device.ReceptorDeviceImpl;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时27分
 */
@Repository
public class DeviceFactoryImpl implements DeviceFactory {

    /**
     * 没有纳入ai控制管控的设备临时存储的地方
     */
    private final Map<String, Device> deviceMap = new HashMap<>();

    @Resource
    private AiDeviceFacade deviceFacade;

    @Override
    public Device getDevice(String uniqueMark) {
        if (deviceMap.containsKey(uniqueMark)) {
            return deviceMap.get(uniqueMark);
        }
        AiDeviceDTO aiDeviceDTO = deviceFacade.findByUniqueMark(uniqueMark);
        if (aiDeviceDTO == null) {
            return null;
        }
        Integer type = aiDeviceDTO.getType();
        AiDeviceStatusEnum statusEnum = AiDeviceStatusEnum.getByCode(type);
        if (AiDeviceStatusEnum.RECEPTOR.equals(statusEnum)) {
            return new ReceptorDeviceImpl(deviceMap, aiDeviceDTO);
        } else if (AiDeviceStatusEnum.CONTROL.equals(statusEnum)) {
            return new ControlDeviceImpl(deviceMap, aiDeviceDTO);
        } else {
            Asserts.throwException("未找到对应的设备,标识:{}", uniqueMark);
            return null;
        }
    }
}
