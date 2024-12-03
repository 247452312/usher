package top.uhyils.usher.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.entity.Device;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时27分
 */
@Repository
public class DeviceFactoryImpl implements DeviceFactory {

    /**
     * 没有纳入ai控制管控的设备临时存储的地方
     */
    private final List<Device> noAiDeviceMap = new ArrayList<>();

    /**
     * 纳入ai控制管控的设备
     */
    private final Map<String, Device> aiDeviceMap = new HashMap<>();

    @Resource
    private AiDeviceFacade deviceFacade;

    @Override
    public Device getDevice(Long deviceId) {
        if (aiDeviceMap.containsKey(deviceId)) {
            return aiDeviceMap.get(deviceId);
        }
        // todo 1.控制中心添加设备连接记录表
        // todo 2.控制中心设备添加唯一标示字段(String)
        // todo 3.控制中心添加根据设备唯一标识获取设备信息以及最近一次连接记录接口
        AiDeviceDTO deviceDTO = deviceFacade.find(deviceId);
        return null;
    }
}
