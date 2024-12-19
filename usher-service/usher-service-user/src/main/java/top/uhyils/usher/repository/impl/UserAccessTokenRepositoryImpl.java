package top.uhyils.usher.repository.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import java.util.List;
import top.uhyils.usher.annotation.Repository;
import top.uhyils.usher.assembler.UserAccessTokenAssembler;
import top.uhyils.usher.dao.UserAccessTokenDao;
import top.uhyils.usher.pojo.DO.UserAccessTokenDO;
import top.uhyils.usher.pojo.DTO.UserAccessTokenDTO;
import top.uhyils.usher.pojo.entity.UserAccessToken;
import top.uhyils.usher.repository.UserAccessTokenRepository;
import top.uhyils.usher.repository.base.AbstractRepository;


/**
 * 用户对应令牌(UserAccessToken)表 仓库实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
@Repository
public class UserAccessTokenRepositoryImpl extends AbstractRepository<UserAccessToken, UserAccessTokenDO, UserAccessTokenDao, UserAccessTokenDTO, UserAccessTokenAssembler> implements UserAccessTokenRepository {

    protected UserAccessTokenRepositoryImpl(UserAccessTokenAssembler convert, UserAccessTokenDao dao) {
        super(convert, dao);
    }


    @Override
    public List<UserAccessToken> findByUserId(Long id) {
        LambdaQueryChainWrapper<UserAccessTokenDO> wrapper = lambdaQuery();
        wrapper.eq(UserAccessTokenDO::getUserId, id);
        List<UserAccessTokenDO> list = wrapper.list();
        return assembler.listToEntity(list);
    }

    @Override
    public boolean checkRepeat(String accessToken) {
        LambdaQueryChainWrapper<UserAccessTokenDO> wrapper = lambdaQuery();
        wrapper.eq(UserAccessTokenDO::getAccessToken, accessToken);
        Long count = wrapper.count();
        return count != 0;
    }

    @Override
    public UserAccessToken findEnableByAccessToken(String accessToken) {
        LambdaQueryChainWrapper<UserAccessTokenDO> wrapper = lambdaQuery();
        wrapper.eq(UserAccessTokenDO::getAccessToken, accessToken);
        wrapper.ge(UserAccessTokenDO::getExpirationDate, System.currentTimeMillis());

        UserAccessTokenDO one = wrapper.one();
        return assembler.toEntity(one);
    }
}
