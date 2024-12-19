package top.uhyils.usher.pojo.DO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DO.base.BaseDO;

/**
 * 用户对应令牌(UserAccessToken)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
@TableName(value = "sys_user_access_token")
public class UserAccessTokenDO extends BaseDO {

    private static final long serialVersionUID = -1L;

    /**
     * 令牌
     */
    @TableField
    private String accessToken;

    /**
     * 用户id
     */
    @TableField
    private Long userId;

    /**
     * 过期时间
     */
    @TableField
    private Long expirationDate;


    /**
     * 描述
     */
    @TableField("`describe`")
    private String describe;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", getId())
            .append("accessToken", getAccessToken())
            .append("userId", getUserId())
            .append("expirationDate", getExpirationDate())
            .append("describe", getDescribe())
            .toString();
    }
}
