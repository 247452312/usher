package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpaceUserLink extends AbstractDoEntity<AiSpaceUserLinkDO> {

    @Default
    public AiSpaceUserLink(AiSpaceUserLinkDO data) {
        super(data);
    }

    public AiSpaceUserLink(Long id) {
        super(id, new AiSpaceUserLinkDO());
    }

}
