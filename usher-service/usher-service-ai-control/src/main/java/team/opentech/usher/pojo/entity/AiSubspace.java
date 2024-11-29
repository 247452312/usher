package team.opentech.usher.pojo.entity;

import java.util.List;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.bus.Bus;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.event.SubSpaceRemoveParentEvent;
import team.opentech.usher.repository.AiSubspaceConnectionPointRepository;
import team.opentech.usher.repository.base.BaseEntityRepository;
import team.opentech.usher.util.CollectionUtil;

/**
 * 子空间(AiSubspace)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSubspace extends AbstractDoEntity<AiSubspaceDO> {

    /**
     * 连通点
     */
    private List<AiSubspaceConnectionPoint> connectionPoints;

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

    @Override
    public <EN extends AbstractDoEntity<AiSubspaceDO>> void saveSelf(BaseEntityRepository<AiSubspaceDO, EN> repository) {
        super.saveSelf(repository);
    }

    /**
     * 填充连通点
     *
     * @param connectionPoints
     */
    public void fillConnectionPoint(List<AiSubspaceConnectionPoint> connectionPoints) {
        this.connectionPoints = connectionPoints;
    }

    public List<AiSubspaceConnectionPoint> connectionPoints() {
        return this.connectionPoints;
    }

    /**
     * 填充连通点
     *
     * @param subspaceConnectionPointRepository
     */
    public void fillConnectionPoint(AiSubspaceConnectionPointRepository subspaceConnectionPointRepository) {
        this.connectionPoints = subspaceConnectionPointRepository.findBySubspaceId(this.unique);
    }

    /**
     * 创建子空间连通点
     *
     * @param connectionPointRepository
     */
    public void createConnectionPoint(AiSubspaceConnectionPointRepository connectionPointRepository) {
        if (CollectionUtil.isEmpty(this.connectionPoints)) {
            return;
        }
        for (AiSubspaceConnectionPoint connectionPoint : this.connectionPoints) {
            connectionPoint.fillSubspaceId(this.unique);
            connectionPoint.removeId();
            connectionPoint.saveSelf(connectionPointRepository);
        }

    }

    public Long spaceId() {
        return toDataAndValidate().getSpaceId();
    }
}
