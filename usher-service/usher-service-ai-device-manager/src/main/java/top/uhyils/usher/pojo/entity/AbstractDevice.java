package top.uhyils.usher.pojo.entity;

import java.util.Map;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.util.IdUtil;
import top.uhyils.usher.util.SpringUtil;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 18时32分
 */
public abstract class AbstractDevice implements Device {

    /**
     * 观察者
     */
    private final Map<String, Device> obvDeviceMap;

    /**
     * 设备id,从控制层获取, 如果没有,则临时生成一个,等到设备创建成功后,再设置
     */
    private String uniqueMark;

    /**
     * 当前设备是否是控制中心设备
     */
    private Boolean aiDevice;

    /**
     * 控制中心的设备信息
     */
    private AiDeviceDTO deviceDTO;

    public AbstractDevice(Map<String, Device> obvDeviceMap, AiDeviceDTO deviceDTO) {
        this(obvDeviceMap, deviceDTO.getUniqueMark(), true, deviceDTO);
    }

    public AbstractDevice(Map<String, Device> obvDeviceMap) {
        this(obvDeviceMap, Long.toString(SpringUtil.getBean(IdUtil.class).newId()), false, null);
    }

    public AbstractDevice(Map<String, Device> obvDeviceMap, String unique) {
        this(obvDeviceMap, unique, false, null);
    }

    private AbstractDevice(Map<String, Device> obvDeviceMap, String uniqueMark, Boolean aiDevice, AiDeviceDTO deviceDTO) {
        this.obvDeviceMap = obvDeviceMap;
        this.uniqueMark = uniqueMark;
        this.aiDevice = aiDevice;
        this.deviceDTO = deviceDTO;
        obvDeviceMap.put(uniqueMark, this);
    }

    @Override
    public String uniqueMark() {
        return uniqueMark;
    }

    @Override
    public boolean isAiDevice() {
        return aiDevice;
    }

    @Override
    public void changeByDevice(AiDeviceDTO deviceDTO) {
        this.uniqueMark = deviceDTO.getUniqueMark();
        this.aiDevice = true;
        this.deviceDTO = deviceDTO;
    }

    @Override
    public void stop() {
        obvDeviceMap.remove(uniqueMark);
    }


    @Override
    public AiDeviceDTO aiDevice() {
        return deviceDTO;
    }
}
