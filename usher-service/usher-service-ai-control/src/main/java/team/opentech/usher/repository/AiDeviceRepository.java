package team.opentech.usher.repository;

import java.util.List;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.entity.AiDevice;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 设备表(AiDevice)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
public interface AiDeviceRepository extends BaseEntityRepository<AiDeviceDO, AiDevice> {

    /**
     * 根据子空间id查询设备
     *
     * @param subspaceId
     *
     * @return
     */
    List<AiDevice> findBySubSpaceId(Long subspaceId);
}
