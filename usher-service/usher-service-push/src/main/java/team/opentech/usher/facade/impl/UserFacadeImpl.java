package team.opentech.usher.facade.impl;

import team.opentech.usher.annotation.Facade;
import team.opentech.usher.facade.UserFacade;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.pojo.cqe.query.IdsQuery;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.protocol.rpc.UserProvider;
import team.opentech.usher.rpc.annotation.RpcReference;
import java.util.List;


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
    public UserDTO getById(Identifier userId) {
        return userProvider.getUserById(new IdQuery(userId.getId()));
    }

    @Override
    public List<UserDTO> getByIds(List<Long> userIds) {
        return userProvider.getSampleUserByIds(new IdsQuery(userIds));
    }
}
