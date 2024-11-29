package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.opentech.usher.pojo.DO.AiDeviceDO;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.pojo.cqe.CreateDeviceCommand;
import team.opentech.usher.pojo.entity.AiDevice;

/**
 * 设备表(AiDevice)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@Mapper(componentModel = "spring")
public abstract class AiDeviceAssembler extends AbstractAssembler<AiDeviceDO, AiDevice, AiDeviceDTO> {


    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getPosition(),team.opentech.usher.pojo.DTO.Point3D.class))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getAngle(),team.opentech.usher.pojo.DTO.Point3D.class))", target = "angle")
    public abstract AiDeviceDTO toDTO(AiDeviceDO dO);

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getPosition()))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getAngle()))", target = "angle")
    public abstract AiDeviceDO toDo(AiDeviceDTO dto);

    public AiDevice toEntity(CreateDeviceCommand command) {
        AiDeviceDTO dto = toDTO(command);
        return toEntity(dto);
    }

    public abstract AiDeviceDTO toDTO(CreateDeviceCommand command);
}
