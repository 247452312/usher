package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.AiSubspaceDO;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 子空间(AiSubspace)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public interface AiSubspaceRepository extends BaseEntityRepository<AiSubspaceDO, AiSubspace> {

    /**
     * 根据空间id查询
     *
     * @param spaceId
     *
     * @return
     */
    List<AiSubspace> findBySpaceId(Long spaceId);
}
