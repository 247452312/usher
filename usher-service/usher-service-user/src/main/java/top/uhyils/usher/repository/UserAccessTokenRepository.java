package top.uhyils.usher.repository;

import java.util.List;
import top.uhyils.usher.pojo.DO.UserAccessTokenDO;
import top.uhyils.usher.pojo.entity.UserAccessToken;
import top.uhyils.usher.repository.base.BaseEntityRepository;

/**
 * 用户对应令牌(UserAccessToken)表 数据仓库层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
public interface UserAccessTokenRepository extends BaseEntityRepository<UserAccessTokenDO, UserAccessToken> {

    /**
     * 根据用户id获取token
     *
     * @param id
     *
     * @return
     */
    List<UserAccessToken> findByUserId(Long id);

    /**
     * 检查是否重复
     *
     * @param accessToken
     *
     * @return 重复则返回true 不重复则返回false
     */
    boolean checkRepeat(String accessToken);

    /**
     * 查询token对应的未过期用户
     *
     * @param accessToken
     *
     * @return
     */
    UserAccessToken findEnableByAccessToken(String accessToken);
}
