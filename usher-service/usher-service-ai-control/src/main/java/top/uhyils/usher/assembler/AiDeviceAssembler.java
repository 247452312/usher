package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.uhyils.usher.pojo.DO.AiDeviceDO;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.CreateDeviceCommand;
import top.uhyils.usher.pojo.entity.AiDevice;

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
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getPosition(),top.uhyils.usher.pojo.DTO.Point3D.class))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getAngle(),top.uhyils.usher.pojo.DTO.Point3D.class))", target = "angle")
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
