package top.uhyils.usher.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.AiDeviceInstructionAssembler;
import top.uhyils.usher.assembler.AiSubspaceAssembler;
import top.uhyils.usher.pojo.DO.AiDeviceInstructionDO;
import top.uhyils.usher.pojo.DTO.AiDeviceInstructionDTO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.cqe.CopyInstructionsByDeviceIdCommand;
import top.uhyils.usher.pojo.cqe.CreateDeviceInstructionCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.entity.AiDeviceInstruction;
import top.uhyils.usher.pojo.entity.AiSpace;
import top.uhyils.usher.pojo.entity.AiSubspace;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.repository.AiDeviceInstructionRepository;
import top.uhyils.usher.repository.AiSpaceRepository;
import top.uhyils.usher.repository.AiSubspaceRepository;
import top.uhyils.usher.service.AiDeviceInstructionService;
import top.uhyils.usher.util.CollectionUtil;

/**
 * 设备指令表(AiDeviceInstruction)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Service
@ReadWriteMark(tables = {"sys_ai_device_instruction"})
public class AiDeviceInstructionServiceImpl extends AbstractDoService<AiDeviceInstructionDO, AiDeviceInstruction, AiDeviceInstructionDTO, AiDeviceInstructionRepository, AiDeviceInstructionAssembler> implements AiDeviceInstructionService {

    @Resource
    private AiSpaceRepository spaceRepository;

    @Resource
    private AiSubspaceRepository subspaceRepository;

    @Resource
    private AiSubspaceAssembler aiSubspaceAssembler;

    public AiDeviceInstructionServiceImpl(AiDeviceInstructionAssembler assembler, AiDeviceInstructionRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Boolean createDeviceInstruction(CreateDeviceInstructionCommand command) {
        AiDeviceInstruction entity = assem.toEntity(command);
        entity.generateNo();
        entity.saveSelf(rep);
        return Boolean.TRUE;
    }

    @Override
    public Boolean removeDeviceInstruction(IdCommand command) {
        int remove = rep.remove(command.getId());
        return remove == 1;
    }

    @Override
    public List<AiDeviceInstructionDTO> findDeviceInstructionById(IdCommand command) {
        List<AiDeviceInstruction> deviceInstructions = rep.findByDeviceId(command.getId());
        return assem.listEntityToDTO(deviceInstructions);
    }

    @Override
    public Boolean copyInstructionsByDeviceId(CopyInstructionsByDeviceIdCommand command) {
        List<AiDeviceInstruction> instructions = rep.findByDeviceId(command.getSourceDeviceId());
        if (CollectionUtil.isEmpty(instructions)) {
            return Boolean.TRUE;
        }
        instructions = instructions.stream().peek(AbstractDoEntity::removeId).peek(t -> t.changeDevice(command.getTargetDeviceId())).collect(Collectors.toList());
        rep.save(instructions);
        return Boolean.TRUE;
    }

    @Override
    public List<AiSubspaceDTO> findAllSubSpaceInSpaceByDeviceId(IdQuery query) {
        AiSpace space = spaceRepository.findByDeviceId(query.getId());
        List<AiSubspace> bySpaceId = subspaceRepository.findBySpaceId(space.unique);
        return aiSubspaceAssembler.listEntityToDTO(bySpaceId);
    }

    @Override
    public Boolean executeInstruction(IdCommand command) {
        return null;
    }
}
