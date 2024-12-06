package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.uhyils.usher.pojo.DO.AiDeviceRealTimeDO;
import top.uhyils.usher.pojo.DTO.AiDeviceRealTimeDTO;
import top.uhyils.usher.pojo.cqe.CreateDeviceCommand;
import top.uhyils.usher.pojo.entity.AiDeviceRealTime;

/**
 * 设备实时状态表(AiDeviceRealTime)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月06日 13时57分
 */
@Mapper(componentModel = "spring")
public abstract class AiDeviceRealTimeAssembler extends AbstractAssembler<AiDeviceRealTimeDO, AiDeviceRealTime, AiDeviceRealTimeDTO> {


    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getPosition(),top.uhyils.usher.pojo.DTO.Point3D.class))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getAngle(),top.uhyils.usher.pojo.DTO.Point3D.class))", target = "angle")
    public abstract AiDeviceRealTimeDTO toDTO(AiDeviceRealTimeDO dO);

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getPosition()))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getAngle()))", target = "angle")
    public abstract AiDeviceRealTimeDO toDo(AiDeviceRealTimeDTO dto);

    public AiDeviceRealTime toEntity(CreateDeviceCommand command) {
        AiDeviceRealTimeDO aDo = toDo(command);
        return new AiDeviceRealTime(aDo);
    }

    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(command.getPosition()))", target = "position")
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(command.getAngle()))", target = "angle")
    protected abstract AiDeviceRealTimeDO toDo(CreateDeviceCommand command);
}
