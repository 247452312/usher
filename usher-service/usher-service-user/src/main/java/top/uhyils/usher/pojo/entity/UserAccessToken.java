package top.uhyils.usher.pojo.entity;

import top.uhyils.usher.annotation.Default;
import top.uhyils.usher.annotation.NotNull;
import top.uhyils.usher.pojo.DO.UserAccessTokenDO;
import top.uhyils.usher.pojo.entity.base.AbstractDoEntity;
import top.uhyils.usher.util.AccessTokenUtil;

/**
 * 用户对应令牌(UserAccessToken)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月18日 16时09分
 */
public class UserAccessToken extends AbstractDoEntity<UserAccessTokenDO> {

    @Default
    public UserAccessToken(UserAccessTokenDO data) {
        super(data);
    }

    public UserAccessToken(Long id) {
        super(id, new UserAccessTokenDO());
    }

    /**
     * 模糊accessToken
     */
    public void eraseToken() {
        UserAccessTokenDO dataAndValidate = toDataAndValidate();
        String accessToken = dataAndValidate.getAccessToken();
        String targetAccessToken = AccessTokenUtil.eraseAccessToken(accessToken);
        dataAndValidate.setAccessToken(targetAccessToken);
        // 这里没有onUpdate()
    }

    /**
     * 检查是否过期
     *
     * @return true:过期 false:未过期
     */
    @NotNull
    public Boolean checkExpirationDatePass() {
        UserAccessTokenDO dataAndValidate = toDataAndValidate();
        Long expirationDate = dataAndValidate.getExpirationDate();
        return System.currentTimeMillis() > expirationDate;
    }
}
