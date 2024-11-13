package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.AiDeviceInstructionAssembler;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
import team.opentech.usher.pojo.entity.AiDeviceInstruction;
import team.opentech.usher.repository.AiDeviceInstructionRepository;
import team.opentech.usher.service.AiDeviceInstructionService;

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

    public AiDeviceInstructionServiceImpl(AiDeviceInstructionAssembler assembler, AiDeviceInstructionRepository repository) {
        super(assembler, repository);
    }


}
