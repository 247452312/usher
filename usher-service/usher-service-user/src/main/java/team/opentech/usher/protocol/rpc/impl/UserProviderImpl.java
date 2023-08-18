package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.annotation.NoLogin;
import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DO.base.TokenInfo;
import team.opentech.usher.pojo.DTO.LoginDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.DTO.request.ApplyUserCommand;
import team.opentech.usher.pojo.DTO.request.FindUserByNameQuery;
import team.opentech.usher.pojo.DTO.request.LoginCommand;
import team.opentech.usher.pojo.DTO.request.UpdatePasswordCommand;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.BlankCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.IdsQuery;
import team.opentech.usher.pojo.entity.Token;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.Password;
import team.opentech.usher.pojo.entity.type.UserName;
import team.opentech.usher.protocol.rpc.UserProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年04月20日 11时51分
 */
@RpcService
public class UserProviderImpl extends BaseDefaultProvider<UserDTO> implements UserProvider {


    @Autowired
    private UserService service;

    @Override
    public UserDTO getUserById(IdQuery request) {
        Identifier userId = new Identifier(request.getId());
        return service.getUserById(userId);
    }

    @Override
    public String getUserToken(IdQuery request) {
        Identifier userId = new Identifier(request.getId());
        return service.getUserToken(userId);
    }

    @Override
    public TokenInfo getTokenInfoByToken(DefaultCQE request) {
        Token token = new Token(request.getToken());
        return service.getTokenInfoByToken(token);
    }

    @Override
    @Public
    public LoginDTO login(LoginCommand request) {
        UserName username = new UserName(request.getUsername());
        Password password = new Password(request.getPassword());
        return service.login(username, password);
    }

    @Override
    @Public
    public LoginDTO visiterLogin(BlankCommand request) {
        return service.visiterLogin();
    }

    @Override
    public Boolean logout(DefaultCQE request) {
        return service.logout();
    }

    @Override
    public List<UserDTO> getUsers(DefaultCQE request) {
        return service.getUsers();
    }

    @Override
    public UserDTO getUserByToken(DefaultCQE request) {
        return service.getUserByToken();
    }

    @Override
    public String updatePassword(UpdatePasswordCommand request) {
        Password oldPassword = new Password(request.getOldPassword());
        Password newPassword = new Password(request.getNewPassword());
        return service.updatePassword(oldPassword, newPassword);
    }

    @Override
    public String getNameById(IdQuery request) {
        Identifier userId = new Identifier(request.getId());
        return service.getNameById(userId);
    }

    @Override
    public List<UserDTO> getSampleUserByIds(IdsQuery request) {
        List<Identifier> userIds = request.getIds().stream().map(Identifier::new).collect(Collectors.toList());
        return service.getSampleUserByIds(userIds);
    }

    @Override
    @NoLogin
    public Boolean applyUser(ApplyUserCommand request) {
        return service.applyUser(request.getUserDTO());
    }

    @Override
    public Boolean passApply(IdCommand request) {
        return service.passApply(new Identifier(request.getId()));
    }

    @Override
    public Boolean stopUser(IdCommand request) {
        return service.stopUser(new Identifier(request.getId()));
    }

    @Override
    public UserDTO getUserByUserName(FindUserByNameQuery request) {
        return service.getUserByUserName(request);
    }

    @Override
    protected BaseDoService<UserDTO> getService() {
        return service;
    }
}
