package top.uhyils.usher.repository;

import java.util.HashMap;
import java.util.Map;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.pojo.entity.Device;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时27分
 */
@Repository
public class DeviceFactoryImpl implements DeviceFactory {

    /**
     * 全部设备缓存
     */
    private final Map<String, Device> allDeviceMap = new HashMap<>();

    /**
     * 没有纳入ai控制管控的设备临时存储的地方
     */
    private final Map<String, Device> noAiDeviceMap = new HashMap<>();

    /**
     * 纳入ai控制管控的设备
     */
    private final Map<String, Device> aiDeviceMap = new HashMap<>();

}
