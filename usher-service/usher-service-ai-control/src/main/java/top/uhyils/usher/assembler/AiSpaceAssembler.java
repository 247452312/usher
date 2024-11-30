package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.AiSpaceDO;
import top.uhyils.usher.pojo.DTO.AiSpaceDTO;
import top.uhyils.usher.pojo.cqe.SpaceCreateCommand;
import top.uhyils.usher.pojo.entity.AiSpace;

/**
 * 独立空间(AiSpace)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper(componentModel = "spring")
public abstract class AiSpaceAssembler extends AbstractAssembler<AiSpaceDO, AiSpace, AiSpaceDTO> {

    public AiSpace toEntity(SpaceCreateCommand command) {
        return new AiSpace(toDo(command));
    }

    public abstract AiSpaceDO toDo(SpaceCreateCommand command);
}
