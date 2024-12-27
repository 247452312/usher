package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.AiSubspaceConnectionPointDO;
import top.uhyils.usher.pojo.entity.AiSubspaceConnectionPoint;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSubspaceConnectionPointRepository extends BaseEntityRepository<AiSubspaceConnectionPointDO, AiSubspaceConnectionPoint> {

    /**
     * 根据子空间id查询所有连通点
     *
     * @param subspaceId
     *
     * @return
     */
    List<AiSubspaceConnectionPoint> findBySubspaceId(Long subspaceId);
}
