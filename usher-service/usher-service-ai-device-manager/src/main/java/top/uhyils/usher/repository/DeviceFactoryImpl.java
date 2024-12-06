package top.uhyils.usher.repository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.device.core.Device;
import top.uhyils.usher.pojo.entity.device.demo.SingleDevice;
import top.uhyils.usher.pojo.entity.link.Link;
import top.uhyils.usher.pojo.entity.link.http.HttpLink;

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

    /**
     * 根据设备制作连接
     */
    private static Link<?, ?> makeLinkByAiDevice(AiDeviceDTO aiDeviceDTO) {
        // todo 设备表需要添加连接信息,这里想完美解决可能需要低代码帮助
        return new HttpLink(null);
    }

    @Override
    public Device getDevice(String uniqueMark) {
        if (deviceMap.containsKey(uniqueMark)) {
            return deviceMap.get(uniqueMark);
        }
        synchronized (deviceMap) {
            if (deviceMap.containsKey(uniqueMark)) {
                return deviceMap.get(uniqueMark);
            }
            return findDeviceByUniqueMarkFromControl(uniqueMark);
        }
    }

    private Device findDeviceByUniqueMarkFromControl(String uniqueMark) {
        AiDeviceDTO aiDeviceDTO = deviceFacade.findByUniqueMark(uniqueMark);
        if (aiDeviceDTO == null) {
            return null;
        }
        return SingleDevice.buildAiDeviceSingleDevice(deviceMap, aiDeviceDTO, makeLinkByAiDevice(aiDeviceDTO));
    }
}
