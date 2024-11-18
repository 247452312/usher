package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.util.SpringUtil;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpaceUserLink extends AbstractDoEntity<AiSpaceUserLinkDO> {

    private AiSpace aiSpace;

    @Default
    public AiSpaceUserLink(AiSpaceUserLinkDO data) {
        super(data);
    }

    public AiSpaceUserLink(Long id) {
        super(id, new AiSpaceUserLinkDO());
    }

    /**
     * 对应的空间
     */
    public AiSpace space() {
        initAiSpace();
        return aiSpace;
    }

    public Long spaceId() {
        return data.getSpaceId();
    }

    public Long userId() {
        return data.getUserId();
    }
    private void initAiSpace() {
        if (aiSpace != null) {
            return;
        }
        this.aiSpace = SpringUtil.getBean(AiSpaceRepository.class).find(Identifier.build(spaceId()));
    }

}
