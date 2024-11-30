package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.pojo.DO.AiSubspaceConnectionPointDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSubspaceConnectionPoint extends AbstractDoEntity<AiSubspaceConnectionPointDO> {

    @Default
    public AiSubspaceConnectionPoint(AiSubspaceConnectionPointDO data) {
        super(data);
    }

    public AiSubspaceConnectionPoint(Long id) {
        super(id, new AiSubspaceConnectionPointDO());
    }

    /**
     * 填充子空间id
     *
     * @param subspaceId
     */
    public void fillSubspaceId(Long subspaceId) {
        toDataAndValidate().setSpaceId(subspaceId);
    }

}
