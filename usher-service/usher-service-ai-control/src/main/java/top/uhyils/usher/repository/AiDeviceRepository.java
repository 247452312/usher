package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.AiDeviceDO;
import top.uhyils.usher.pojo.entity.AiDevice;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.repository.base.BaseEntityRepository;

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

    /**
     * 根据子空间id删除设备
     *
     * @param subSpaceId
     */
    void removeBySubSpaceId(Long subSpaceId);

    /**
     * 根据子空间id集合查询设备
     *
     * @param subSpaceIds
     *
     * @return
     */
    List<AiDevice> findBySubSpaceIds(List<Long> subSpaceIds);

    /**
     * 根据设备id获取所在子空间
     *
     * @param deviceId
     *
     * @return
     */
    AiSubspace findSubSpaceById(Long deviceId);
}
