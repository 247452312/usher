package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiSpaceAssembler;
import team.opentech.usher.dao.AiSpaceDao;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 独立空间(AiSpace)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSpaceRepositoryImpl extends AbstractRepository<AiSpace, AiSpaceDO, AiSpaceDao, AiSpaceDTO, AiSpaceAssembler> implements AiSpaceRepository {

    protected AiSpaceRepositoryImpl(AiSpaceAssembler convert, AiSpaceDao dao) {
        super(convert, dao);
    }


}
