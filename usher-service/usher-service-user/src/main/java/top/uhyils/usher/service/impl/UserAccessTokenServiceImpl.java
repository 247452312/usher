package top.uhyils.usher.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.UserAccessTokenAssembler;
import top.uhyils.usher.enums.UserTokenValidityEnum;
import top.uhyils.usher.pojo.DO.UserAccessTokenDO;
import top.uhyils.usher.pojo.DTO.LoginDTO;
import top.uhyils.usher.pojo.DTO.UserAccessTokenDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.RandomCreateTokenCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.pojo.entity.UserAccessToken;
import top.uhyils.usher.repository.UserAccessTokenRepository;
import top.uhyils.usher.service.UserAccessTokenService;
import top.uhyils.usher.service.UserService;
import top.uhyils.usher.util.AccessTokenUtil;
import top.uhyils.usher.util.Asserts;

/**
 * 用户对应令牌(UserAccessToken)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
@Service
@ReadWriteMark(tables = {"sys_user_access_token"})
public class UserAccessTokenServiceImpl extends AbstractDoService<UserAccessTokenDO, UserAccessToken, UserAccessTokenDTO, UserAccessTokenRepository, UserAccessTokenAssembler> implements UserAccessTokenService {

    @Resource
    private UserService userService;


    public UserAccessTokenServiceImpl(UserAccessTokenAssembler assembler, UserAccessTokenRepository repository) {
        super(assembler, repository);
    }

    @Override
    public UserAccessTokenDTO randomCreateToken(RandomCreateTokenCommand command) {
        String describe = command.getDescribe();
        Integer validityCode = command.getValidityCode();
        UserTokenValidityEnum validity = UserTokenValidityEnum.getByCode(validityCode);
        Asserts.assertTrue(validity != null, "时效必须选择");
        String accessToken = AccessTokenUtil.makeAccessToken();
        while (rep.checkRepeat(accessToken)) {
            accessToken = AccessTokenUtil.makeAccessToken();
        }
        UserAccessToken entity = assem.toEntity(accessToken, describe, validity.makeExpirationDate(), command.getUser().getId());
        entity.saveSelf(rep);
        return assem.toDTO(entity);
    }

    @Override
    public Boolean deleteToken(IdCommand command) {
        return rep.remove(command.getId()) == 1;
    }

    @Override
    public List<UserAccessTokenDTO> findAllToken(DefaultCQE defaultCQE) {
        List<UserAccessToken> tokens = rep.findByUserId(defaultCQE.getUser().getId());
        tokens.forEach(UserAccessToken::eraseToken);
        return assem.listEntityToDTO(tokens);
    }

    @Override
    public LoginDTO accessToken(StringCommand command) {
        String accessToken = command.getValue();
        UserAccessToken token = rep.findEnableByAccessToken(accessToken);
        Boolean pass = token.checkExpirationDatePass();
        Asserts.assertTrue(!pass, "token已过期");
        return userService.login(assem.toDTO(token));
    }

    @Override
    public Boolean checkAccessToken(StringCommand command) {
        return Boolean.TRUE;
    }
}
