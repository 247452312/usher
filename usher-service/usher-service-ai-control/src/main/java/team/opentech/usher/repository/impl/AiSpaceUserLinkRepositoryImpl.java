package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiSpaceUserLinkAssembler;
import team.opentech.usher.dao.AiSpaceUserLinkDao;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.DTO.AiSpaceUserLinkDTO;
import team.opentech.usher.pojo.entity.AiSpaceUserLink;
import team.opentech.usher.repository.AiSpaceUserLinkRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSpaceUserLinkRepositoryImpl extends AbstractRepository<AiSpaceUserLink, AiSpaceUserLinkDO, AiSpaceUserLinkDao, AiSpaceUserLinkDTO, AiSpaceUserLinkAssembler> implements AiSpaceUserLinkRepository {

    protected AiSpaceUserLinkRepositoryImpl(AiSpaceUserLinkAssembler convert, AiSpaceUserLinkDao dao) {
        super(convert, dao);
    }


}
