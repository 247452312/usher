package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.UserAccessTokenDO;
import top.uhyils.usher.pojo.DTO.UserAccessTokenDTO;
import top.uhyils.usher.pojo.entity.UserAccessToken;

/**
 * 用户对应令牌(UserAccessToken)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
@Mapper(componentModel = "spring")
public abstract class UserAccessTokenAssembler extends AbstractAssembler<UserAccessTokenDO, UserAccessToken, UserAccessTokenDTO> {

    /**
     * 创建一个新的token
     *
     * @param accessToken
     * @param describe
     * @param expirationDate
     * @param userId
     *
     * @return
     */
    public UserAccessToken toEntity(String accessToken, String describe, Long expirationDate, Long userId) {
        UserAccessTokenDO data = new UserAccessTokenDO();
        data.setAccessToken(accessToken);
        data.setExpirationDate(expirationDate);
        data.setUserId(userId);
        data.setDescribe(describe);
        return new UserAccessToken(data);
    }
}
