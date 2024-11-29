package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月18日 14时36分
 */
public class UpdateSpaceInfoCommand extends AbstractCommand {

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 空间名称
     */
    private String spaceName;


    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }
}
