package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiSpaceAssembler;
import team.opentech.usher.pojo.DO.AiSpaceDO;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.repository.AiSpaceRepository;
import team.opentech.usher.service.AiSpaceService;

/**
 * 独立空间(AiSpace)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_space"})
public class AiSpaceServiceImpl extends AbstractDoService<AiSpaceDO, AiSpace, AiSpaceDTO, AiSpaceRepository, AiSpaceAssembler> implements AiSpaceService {

    public AiSpaceServiceImpl(AiSpaceAssembler assembler, AiSpaceRepository repository) {
        super(assembler, repository);
    }


}
