package top.uhyils.usher.repository;

import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;
import top.uhyils.usher.pojo.entity.AiDeviceRealTime;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 设备实时状态表(AiDeviceRealTime)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
public interface AiDeviceRealTimeRepository extends BaseEntityRepository<AiDeviceRealTimeDO, AiDeviceRealTime> {

    /**
     * 根据设备id获取实时数据
     *
     * @param deviceId
     *
     * @return
     */
    AiDeviceRealTime findByDevice(Long deviceId);

    /**
     * 根据唯一标示获取实时数据
     *
     * @param value
     *
     * @return
     */
    AiDeviceRealTime findByUnique(String value);
}
