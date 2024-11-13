package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiSubspaceConnectionPointDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

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

}
