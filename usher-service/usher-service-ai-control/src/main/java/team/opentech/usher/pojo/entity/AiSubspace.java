package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.event.SubSpaceRemoveParentEvent;
import team.opentech.usher.repository.base.BaseEntityRepository;

/**
 * 子空间(AiSubspace)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSubspace extends AbstractDoEntity<AiSubspaceDO> {

    @Default
    public AiSubspace(AiSubspaceDO data) {
        super(data);
    }

    public AiSubspace(Long id) {
        super(id, new AiSubspaceDO());
    }

    @Override
    public <EN extends AbstractDoEntity<AiSubspaceDO>> void removeSelf(BaseEntityRepository<AiSubspaceDO, EN> repository) {
        Bus.single().commitAndPush(new SubSpaceRemoveParentEvent(this));
        super.removeSelf(repository);
    }
}
