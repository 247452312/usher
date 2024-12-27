package top.uhyils.usher.assembler;


import javax.annotation.Resource;
import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.AiDeviceDO;
import top.uhyils.usher.pojo.DTO.AiDeviceAndRealTimeDTO;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.CreateDeviceCommand;
import top.uhyils.usher.pojo.entity.AiDevice;
import top.uhyils.usher.pojo.entity.AiDeviceRealTime;

/**
 * 设备表(AiDevice)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Mapper(componentModel = "spring")
public abstract class AiDeviceAssembler extends AbstractAssembler<AiDeviceDO, AiDevice, AiDeviceDTO> {


    @Resource
    private AiDeviceRealTimeAssembler realTimeAssembler;

    public AiDevice toEntity(CreateDeviceCommand command) {
        AiDeviceDTO dto = toDTO(command);
        AiDevice entity = toEntity(dto);
        AiDeviceRealTime realTime = realTimeAssembler.toEntity(command);
        entity.fillRealTime(realTime);
        return entity;
    }

    public abstract AiDeviceDTO toDTO(CreateDeviceCommand command);

    public AiDeviceAndRealTimeDTO toDTO(AiDevice aiDeviceDTO, AiDeviceRealTime realTime) {
        AiDeviceAndRealTimeDTO aiDeviceAndRealTimeDTO = new AiDeviceAndRealTimeDTO();
        aiDeviceAndRealTimeDTO.setDeviceDTO(toDTO(aiDeviceDTO));
        aiDeviceAndRealTimeDTO.setRealTimeDTO(realTimeAssembler.toDTO(realTime));
        return aiDeviceAndRealTimeDTO;
    }
}
