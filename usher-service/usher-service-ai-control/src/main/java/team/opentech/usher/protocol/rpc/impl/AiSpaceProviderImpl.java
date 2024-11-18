package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import javax.annotation.Resource;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.DTO.UserDTO;
import team.opentech.usher.pojo.cqe.AddUserToSpaceCommand;
import team.opentech.usher.pojo.cqe.CreateSubSpaceCommand;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.FindSubSpaceBySpaceIdQuery;
import team.opentech.usher.pojo.cqe.RemoveSpaceCommand;
import team.opentech.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import team.opentech.usher.pojo.cqe.SpaceCreateCommand;
import team.opentech.usher.pojo.cqe.UpdateSpaceInfoCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.AiSpaceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiSpaceService;
import team.opentech.usher.service.BaseDoService;

/**
 * 独立空间(AiSpace)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@RpcService
public class AiSpaceProviderImpl extends BaseDefaultProvider<AiSpaceDTO> implements AiSpaceProvider {


    @Resource
    private AiSpaceService service;

    @Override
    public Boolean create(SpaceCreateCommand command) {
        return service.create(command);
    }

    @Override
    public Boolean addUserToSpace(AddUserToSpaceCommand command) {
        return service.addUserToSpace(command);
    }

    @Override
    public Boolean removeUserFromSpace(RemoveUserFromSpaceCommand command) {
        return service.removeUserFromSpace(command);
    }

    @Override
    public List<UserDTO> findUserBySpaceId(IdQuery query) {
        return service.findUserBySpaceId(query);
    }

    @Override
    public Boolean createSubSpace(CreateSubSpaceCommand command) {
        return service.createSubSpace(command);
    }


    @Override
    public Boolean removeSpace(RemoveSpaceCommand command) {
        return service.removeSpace(command);
    }

    @Override
    public Boolean updateSpaceInfo(UpdateSpaceInfoCommand command) {
        return service.updateSpaceInfo(command);
    }

    @Override
    public List<AiSpaceDTO> findByOnlineUser(DefaultCQE blackQuery) {
        return service.findByOnlineUser(blackQuery);
    }

    @Override
    public List<AiSubspaceDTO> findSubSpaceBySpaceId(FindSubSpaceBySpaceIdQuery query) {
        return service.findSubSpaceBySpaceId(query);
    }


    @Override
    protected BaseDoService<AiSpaceDTO> getService() {
        return service;
    }

}

