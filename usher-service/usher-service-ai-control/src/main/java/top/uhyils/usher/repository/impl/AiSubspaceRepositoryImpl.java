package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.AiSubspaceAssembler;
import top.uhyils.usher.dao.AiSubspaceDao;
import top.uhyils.usher.pojo.DO.AiSubspaceDO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.repository.AiSubspaceRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


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


    @Override
    public List<AiSubspace> findBySpaceId(Long spaceId) {
        LambdaQueryChainWrapper<AiSubspaceDO> wrapper = lambdaQuery();
        wrapper.eq(AiSubspaceDO::getSpaceId, spaceId);
        List<AiSubspaceDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }
}
