package top.uhyils.usher.facade.impl;

import java.util.List;
import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.UserFacade;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.pojo.cqe.query.IdsQuery;
import top.uhyils.usher.protocol.rpc.UserProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月04日 08时52分
 */
@Facade
public class UserFacadeImpl implements UserFacade {

    @RpcReference
    private UserProvider userProvider;

    @Override
    public UserDTO getById(Long userId) {
        return userProvider.getUserById(new IdQuery(userId));
    }

    @Override
    public List<UserDTO> getByIds(List<Long> userIds) {
        return userProvider.getSampleUserByIds(new IdsQuery(userIds));
    }
}
