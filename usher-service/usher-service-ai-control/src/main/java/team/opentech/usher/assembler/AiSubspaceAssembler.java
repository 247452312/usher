package team.opentech.usher.assembler;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import java.util.List;
import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.AiSubspaceDO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.DTO.EdgeCoordinaties;
import team.opentech.usher.pojo.DTO.Point3D;
import team.opentech.usher.pojo.cqe.CreateSubSpaceCommand;
import team.opentech.usher.pojo.entity.AiSubspace;

/**
 * 子空间(AiSubspace)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper(componentModel = "spring")
public abstract class AiSubspaceAssembler extends AbstractAssembler<AiSubspaceDO, AiSubspace, AiSubspaceDTO> {

    @Override
    public AiSubspaceDTO toDTO(AiSubspaceDO dO) {
        AiSubspaceDTO aiSubspaceDTO = new AiSubspaceDTO();
        aiSubspaceDTO.setId(dO.getId());
        aiSubspaceDTO.setName(dO.getName());
        aiSubspaceDTO.setSpaceId(dO.getSpaceId());
        List<EdgeCoordinaties> edgeCoordinaties = JSONArray.parseArray(dO.getEdgeCoordinates(), EdgeCoordinaties.class);
        aiSubspaceDTO.setEdgeCoordinates(edgeCoordinaties);
        Point3D originRelativeCoordinates = JSONObject.parseObject(dO.getOriginRelativeCoordinates(), Point3D.class);
        aiSubspaceDTO.setOriginRelativeCoordinates(originRelativeCoordinates);
        return aiSubspaceDTO;
    }

    @Override
    public AiSubspaceDO toDo(AiSubspaceDTO dto) {
        AiSubspaceDO aiSubspaceDO = new AiSubspaceDO();
        aiSubspaceDO.setName(dto.getName());
        aiSubspaceDO.setSpaceId(dto.getSpaceId());
        aiSubspaceDO.setId(dto.getId());
        List<EdgeCoordinaties> edgeCoordinates = dto.getEdgeCoordinates();
        aiSubspaceDO.setEdgeCoordinates(JSON.toJSONString(edgeCoordinates));
        aiSubspaceDO.setOriginRelativeCoordinates(JSON.toJSONString(dto.getOriginRelativeCoordinates()));
        return aiSubspaceDO;
    }

    public AiSubspace toEntity(CreateSubSpaceCommand command) {
        AiSubspaceDO aDo = toDo(command.getSubspaceDTO());
        aDo.setSpaceId(command.getSpaceId());
        return toEntity(aDo);
    }
}
