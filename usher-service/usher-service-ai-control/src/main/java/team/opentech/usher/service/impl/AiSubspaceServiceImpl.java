package team.opentech.usher.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiSubspaceAssembler;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.pojo.event.CleanSubSpaceEvent;
import team.opentech.usher.repository.AiSubspaceRepository;
import team.opentech.usher.service.AiSubspaceService;

/**
 * 子空间(AiSubspace)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_subspace"})
public class AiSubspaceServiceImpl extends AbstractDoService<AiSubspaceDO, AiSubspace, AiSubspaceDTO, AiSubspaceRepository, AiSubspaceAssembler> implements AiSubspaceService {

    public AiSubspaceServiceImpl(AiSubspaceAssembler assembler, AiSubspaceRepository repository) {
        super(assembler, repository);
    }


    @Override
    public void cleanSubSpaceEvent(CleanSubSpaceEvent event) {
        List<AiSubspace> subspaces = rep.findBySpaceId(event.getSpaceId());
        for (AiSubspace subspace : subspaces) {
            subspace.removeSelf(rep);
        }

    }
}
