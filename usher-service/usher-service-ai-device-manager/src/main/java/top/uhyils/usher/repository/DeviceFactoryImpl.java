package top.uhyils.usher.repository;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.enums.AiDeviceLinkTypeEnum;
import top.uhyils.usher.enums.LinkMethodEnum;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceAndRealTimeDTO;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.DTO.AiDeviceRealTimeDTO;
import top.uhyils.usher.pojo.entity.device.core.Device;
import top.uhyils.usher.pojo.entity.device.demo.SingleDevice;
import top.uhyils.usher.pojo.entity.link.Link;
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

    /**
     * 根据设备制作连接
     */
    private static Link makeLinkByAiDevice(AiDeviceDTO aiDeviceDTO, AiDeviceRealTimeDTO realTimeDTO) {
        AiDeviceLinkTypeEnum linkType = AiDeviceLinkTypeEnum.getByCode(aiDeviceDTO.getLinkType());
        LinkMethodEnum linkMethod = LinkMethodEnum.getByCode(linkType);
        Asserts.assertTrue(linkMethod != null, "组装设备链接过程失败");
        return linkMethod.makeLink(aiDeviceDTO.getLinkContent(), realTimeDTO);
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

    /**
     * 获取设备信息
     */
    private Device findDeviceByUniqueMarkFromControl(String uniqueMark) {
        AiDeviceAndRealTimeDTO aiDeviceDTO = deviceFacade.findByUniqueMark(uniqueMark);
        if (aiDeviceDTO == null) {
            return null;
        }
        AiDeviceDTO deviceDTO = aiDeviceDTO.getDeviceDTO();
        Link link = makeLinkByAiDevice(deviceDTO, aiDeviceDTO.getRealTimeDTO());
        return SingleDevice.buildAiDeviceSingleDevice(deviceMap, deviceDTO, link);
    }
}
