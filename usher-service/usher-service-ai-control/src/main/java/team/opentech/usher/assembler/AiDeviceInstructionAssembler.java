package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.AiDeviceInstructionDO;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
import team.opentech.usher.pojo.entity.AiDeviceInstruction;

/**
 * 设备指令表(AiDeviceInstruction)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Mapper(componentModel = "spring")
public abstract class AiDeviceInstructionAssembler extends AbstractAssembler<AiDeviceInstructionDO, AiDeviceInstruction, AiDeviceInstructionDTO> {

}
