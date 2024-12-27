package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@TableName(value = "sys_ai_space_user_link")
public class AiSpaceUserLinkDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 独立空间id
     */
    @TableField
    private Long spaceId;

    /**
     * 所属用户id
     */
    @TableField
    private Long userId;

    /**
     * 是否是管理员
     */
    @TableField
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
