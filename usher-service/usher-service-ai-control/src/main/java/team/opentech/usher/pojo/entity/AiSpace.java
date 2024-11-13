package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;

/**
 * 独立空间(AiSpace)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpace extends AbstractDoEntity<AiSpaceDO> {

    @Default
    public AiSpace(AiSpaceDO data) {
        super(data);
    }

    public AiSpace(Long id) {
        super(id, new AiSpaceDO());
    }

}
