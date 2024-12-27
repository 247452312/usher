package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.annotation.AccessApi;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.pojo.DTO.LoginDTO;
import top.uhyils.usher.pojo.DTO.UserAccessTokenDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.RandomCreateTokenCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.protocol.rpc.UserAccessTokenProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.UserAccessTokenService;

/**
 * 用户对应令牌(UserAccessToken)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年12月18日 16时09分
 */
@RpcService
public class UserAccessTokenProviderImpl extends BaseDefaultProvider<UserAccessTokenDTO> implements UserAccessTokenProvider {


    @Resource
    private UserAccessTokenService service;

    @Override
    public UserAccessTokenDTO randomCreateToken(RandomCreateTokenCommand command) {
        return service.randomCreateToken(command);
    }

    @Override
    public Boolean deleteToken(IdCommand command) {
        return service.deleteToken(command);
    }

    @Override
    public List<UserAccessTokenDTO> findAllToken(DefaultCQE defaultCQE) {
        return service.findAllToken(defaultCQE);
    }

    @Override
    @Public
    public LoginDTO accessToken(StringCommand command) {
        return service.accessToken(command);
    }

    @Override
    @AccessApi
    public Boolean checkAccessToken(StringCommand command) {
        return service.checkAccessToken(command);
    }


    @Override
    protected BaseDoService<UserAccessTokenDTO> getService() {
        return service;
    }

}

