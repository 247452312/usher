package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.AiSpaceUserLinkDO;
import team.opentech.usher.pojo.DTO.AiSpaceUserLinkDTO;
import team.opentech.usher.pojo.entity.AiSpace;
import team.opentech.usher.pojo.entity.AiSpaceUserLink;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@Mapper(componentModel = "spring")
public abstract class AiSpaceUserLinkAssembler extends AbstractAssembler<AiSpaceUserLinkDO, AiSpaceUserLink, AiSpaceUserLinkDTO> {

    public AiSpaceUserLink toEntity(Long userId, Boolean isAdmin, AiSpace aiSpace) {
        AiSpaceUserLinkDO data = new AiSpaceUserLinkDO();
        data.setSpaceId(aiSpace.unique.getId());
        data.setUserId(userId);
        data.setIsAdmin(isAdmin);
        return new AiSpaceUserLink(data);
    }
}
