package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.opentech.usher.pojo.DO.AiSubspaceConnectionPointDO;
import team.opentech.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import team.opentech.usher.pojo.entity.AiSubspaceConnectionPoint;

/**
 * 子空间连通点(AiSubspaceConnectionPoint)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper(componentModel = "spring")
public abstract class AiSubspaceConnectionPointAssembler extends AbstractAssembler<AiSubspaceConnectionPointDO, AiSubspaceConnectionPoint, AiSubspaceConnectionPointDTO> {

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getPointCoordinate(),team.opentech.usher.pojo.DTO.Point3D.class))", target = "pointCoordinate")
    public abstract AiSubspaceConnectionPointDTO toDTO(AiSubspaceConnectionPointDO dO);

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getPointCoordinate()))", target = "pointCoordinate")
    public abstract AiSubspaceConnectionPointDO toDo(AiSubspaceConnectionPointDTO dto);

}
