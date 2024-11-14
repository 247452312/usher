package team.opentech.usher.pojo.cqe;

import team.opentech.usher.pojo.cqe.command.base.AbstractCommand;

/**
 * 添加用户到空间
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月14日 15时48分
 */
public class AddUserToSpaceCommand extends AbstractCommand {

    /**
     * 空间id
     */
    private Long spaceId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否是管理员(默认不是)
     */
    private Boolean isAdmin = Boolean.FALSE;

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
