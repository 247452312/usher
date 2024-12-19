package top.uhyils.usher.pojo.DTO;

import org.apache.commons.lang3.builder.ToStringBuilder;
import top.uhyils.usher.pojo.DTO.base.IdDTO;

/**
 * 用户对应令牌表(UserAccessToken)表 对外数据传输载体
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
public class UserAccessTokenDTO extends IdDTO {

    private static final long serialVersionUID = -1L;

    /**
     * 令牌
     */
    private String accessToken;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 过期时间
     */
    private Long expirationDate;

    /**
     * 描述
     */
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
            .append("expirationDate", getExpirationDate())
            .append("userId", getUserId())
            .append("describe", getDescribe())
            .toString();
    }

}
