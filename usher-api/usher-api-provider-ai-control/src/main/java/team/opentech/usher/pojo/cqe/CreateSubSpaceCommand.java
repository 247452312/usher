package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 17时22分
 */
public class CreateSubSpaceCommand extends AbstractCommand {

    /**
     * 所属空间id
     */
    private Long spaceId;

    /**
     * 子空间需要的属性
     */
    private AiSubspaceDTO subspaceDTO;

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public AiSubspaceDTO getSubspaceDTO() {
        return subspaceDTO;
    }

    public void setSubspaceDTO(AiSubspaceDTO subspaceDTO) {
        this.subspaceDTO = subspaceDTO;
    }
}
