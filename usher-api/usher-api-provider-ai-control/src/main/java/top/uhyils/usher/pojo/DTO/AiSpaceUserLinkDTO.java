package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 独立空间-用户关联表表(AiSpaceUserLink)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
public class AiSpaceUserLinkDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 独立空间id
     */
    private Long spaceId;

    /**
     * 所属用户id
     */
    private Long userId;

    /**
     * 是否是管理员
     */
    private Boolean isAdmin;

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public Long getSpaceId() {
        return spaceId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("spaceId", getSpaceId())
            .append("userId", getUserId())
            .append("isAdmin", getIsAdmin())
            .toString();
    }

}
