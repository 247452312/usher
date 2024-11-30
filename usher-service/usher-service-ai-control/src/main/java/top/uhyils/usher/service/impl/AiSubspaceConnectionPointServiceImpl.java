package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.AiSubspaceConnectionPointAssembler;
import top.uhyils.usher.pojo.DO.AiSubspaceConnectionPointDO;
import top.uhyils.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import top.uhyils.usher.pojo.entity.AiSubspaceConnectionPoint;
import top.uhyils.usher.repository.AiSubspaceConnectionPointRepository;
import top.uhyils.usher.service.AiSubspaceConnectionPointService;

/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_subspace_connection_point"})
public class AiSubspaceConnectionPointServiceImpl extends AbstractDoService<AiSubspaceConnectionPointDO, AiSubspaceConnectionPoint, AiSubspaceConnectionPointDTO, AiSubspaceConnectionPointRepository, AiSubspaceConnectionPointAssembler> implements AiSubspaceConnectionPointService {

    public AiSubspaceConnectionPointServiceImpl(AiSubspaceConnectionPointAssembler assembler, AiSubspaceConnectionPointRepository repository) {
        super(assembler, repository);
    }


}
