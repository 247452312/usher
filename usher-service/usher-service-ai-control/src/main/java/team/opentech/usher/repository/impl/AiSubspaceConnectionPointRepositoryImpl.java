package team.opentech.usher.repository.impl;

import team.opentech.usher.annotation.Repository;
import team.opentech.usher.assembler.AiSubspaceConnectionPointAssembler;
import team.opentech.usher.dao.AiSubspaceConnectionPointDao;
import team.opentech.usher.pojo.DO.AiSubspaceConnectionPointDO;
import team.opentech.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import team.opentech.usher.pojo.entity.AiSubspaceConnectionPoint;
import team.opentech.usher.repository.AiSubspaceConnectionPointRepository;
import team.opentech.usher.repository.base.AbstractRepository;


/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Repository
public class AiSubspaceConnectionPointRepositoryImpl extends AbstractRepository<AiSubspaceConnectionPoint, AiSubspaceConnectionPointDO, AiSubspaceConnectionPointDao, AiSubspaceConnectionPointDTO, AiSubspaceConnectionPointAssembler> implements AiSubspaceConnectionPointRepository {

    protected AiSubspaceConnectionPointRepositoryImpl(AiSubspaceConnectionPointAssembler convert, AiSubspaceConnectionPointDao dao) {
        super(convert, dao);
    }


}
