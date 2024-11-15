package team.opentech.usher.assembler;


import java.util.List;
import javax.annotation.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.DTO.AiSubspaceConnectionPointDTO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.cqe.CreateSubSpaceCommand;
import team.opentech.usher.pojo.entity.AiSubspace;
import team.opentech.usher.pojo.entity.AiSubspaceConnectionPoint;

/**
 * 子空间(AiSubspace)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper(componentModel = "spring")
public abstract class AiSubspaceAssembler extends AbstractAssembler<AiSubspaceDO, AiSubspace, AiSubspaceDTO> {

    @Resource
    private AiSubspaceConnectionPointAssembler connectionPointAssembler;

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSONArray.parseArray(dO.getEdgeCoordinates(),team.opentech.usher.pojo.DTO.EdgeCoordinaties.class))", target = "edgeCoordinates")
    @Mapping(expression = "java(com.alibaba.fastjson.JSONObject.parseObject(dO.getOriginRelativeCoordinates(),team.opentech.usher.pojo.DTO.Point3D.class))", target = "originRelativeCoordinates")
    public abstract AiSubspaceDTO toDTO(AiSubspaceDO dO);

    @Override
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getEdgeCoordinates()))", target = "edgeCoordinates")
    @Mapping(expression = "java(com.alibaba.fastjson.JSON.toJSONString(dto.getOriginRelativeCoordinates()))", target = "originRelativeCoordinates")
    public abstract AiSubspaceDO toDo(AiSubspaceDTO dto);

    public AiSubspace toEntity(CreateSubSpaceCommand command) {
        AiSubspaceDTO subspaceDTO = command.getSubspaceDTO();
        AiSubspaceDO aDo = toDo(subspaceDTO);
        aDo.setSpaceId(command.getSpaceId());
        AiSubspace entity = toEntity(aDo);
        List<AiSubspaceConnectionPoint> aiSubspaceConnectionPoints = connectionPointAssembler.listDTOToEntity(subspaceDTO.getConnectionPointDTOS());
        entity.fillConnectionPoint(aiSubspaceConnectionPoints);
        return entity;
    }

    @Override
    public AiSubspaceDTO toDTO(AiSubspace entity) {
        AiSubspaceDTO dto = toDTO(entity.toDataAndValidate());
        List<AiSubspaceConnectionPoint> aiSubspaceConnectionPoints = entity.connectionPoints();
        List<AiSubspaceConnectionPointDTO> aiSubspaceConnectionPointDTOS = connectionPointAssembler.listEntityToDTO(aiSubspaceConnectionPoints);
        dto.setConnectionPointDTOS(aiSubspaceConnectionPointDTOS);
        return dto;
    }
}
