package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiSubspaceAssembler;
import team.opentech.usher.dao.AiSubspaceDao;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.repository.AiSubspaceRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 子空间(AiSubspace)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSubspaceRepositoryImpl extends AbstractRepository<AiSubspace, AiSubspaceDO, AiSubspaceDao, AiSubspaceDTO, AiSubspaceAssembler> implements AiSubspaceRepository {

    protected AiSubspaceRepositoryImpl(AiSubspaceAssembler convert, AiSubspaceDao dao) {
        super(convert, dao);
    }


}
