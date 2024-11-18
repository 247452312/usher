package team.opentech.usher.facade.impl;

import java.util.List;
import team.opentech.usher.annotation.Facade;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.facade.UserFacade;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.query.IdsQuery;
import team.opentech.usher.protocol.rpc.UserProvider;
import team.opentech.usher.rpc.annotation.RpcReference;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年11月18日 14时49分
 */
@Facade
public class UserFacadeImpl implements UserFacade {


    @RpcReference
    private UserProvider userProvider;

    @Override
    public List<UserDTO> findByUserIdList(List<Long> userIds) {
        IdsQuery request = new IdsQuery();
        request.setIds(userIds);
        UserInfoHelper.fillCQE(request);
        return userProvider.getSampleUserByIds(request);
    }
}
