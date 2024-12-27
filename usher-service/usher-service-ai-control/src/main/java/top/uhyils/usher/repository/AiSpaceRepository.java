package top.uhyils.usher.repository;

import top.uhyils.usher.pojo.DO.AiSpaceDO;
import top.uhyils.usher.pojo.entity.AiSpace;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 独立空间(AiSpace)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSpaceRepository extends BaseEntityRepository<AiSpaceDO, AiSpace> {

    /**
     * 根据设备id获取独立空间
     *
     * @param deviceId
     *
     * @return
     */
    AiSpace findByDeviceId(Long deviceId);
}
