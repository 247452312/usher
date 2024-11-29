package team.opentech.usher.repository;

import java.util.List;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.entity.AiDeviceInstruction;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 设备指令表(AiDeviceInstruction)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceInstructionRepository extends BaseEntityRepository<AiDeviceInstructionDO, AiDeviceInstruction> {

    /**
     * 根据设备id获取指令
     *
     * @param deviceId
     *
     * @return
     */
    List<AiDeviceInstruction> findByDeviceId(Long deviceId);

    /**
     * 根据设备id删除指令
     *
     * @param deviceId
     */
    void removeByDeviceId(Long deviceId);
}
