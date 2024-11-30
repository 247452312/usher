package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AiSubspaceConnectionPointAssembler;
import top.uhyils.usher.dao.AiSubspaceConnectionPointDao;
import top.uhyils.usher.pojo.DO.AiSubspaceConnectionPointDO;
import top.uhyils.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import top.uhyils.usher.pojo.entity.AiSubspaceConnectionPoint;
import top.uhyils.usher.repository.AiSubspaceConnectionPointRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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


    @Override
    public List<AiSubspaceConnectionPoint> findBySubspaceId(Long subspaceId) {
        LambdaQueryChainWrapper<AiSubspaceConnectionPointDO> wrapper = lambdaQuery();
        wrapper.eq(AiSubspaceConnectionPointDO::getSpaceId, subspaceId);
        List<AiSubspaceConnectionPointDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }
}
