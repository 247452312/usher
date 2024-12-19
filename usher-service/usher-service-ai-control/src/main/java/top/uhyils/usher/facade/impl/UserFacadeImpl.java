package top.uhyils.usher.facade.impl;

import java.util.List;
import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.context.LoginInfoHelper;
import top.uhyils.usher.facade.UserFacade;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.query.IdsQuery;
import top.uhyils.usher.protocol.rpc.UserProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;

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
        LoginInfoHelper.fillCQE(request);
        return userProvider.getSampleUserByIds(request);
    }
}
