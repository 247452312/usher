package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import javax.annotation.Resource;
import top.uhyils.usher.pojo.DTO.AiSpaceDTO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.DTO.UserDTO;
import top.uhyils.usher.pojo.cqe.AddUserToSpaceCommand;
import top.uhyils.usher.pojo.cqe.ChangeSubSpaceCommand;
import top.uhyils.usher.pojo.cqe.CreateSubSpaceCommand;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.FindSubSpaceBySpaceIdQuery;
import top.uhyils.usher.pojo.cqe.RemoveSpaceCommand;
import top.uhyils.usher.pojo.cqe.RemoveUserFromSpaceCommand;
import top.uhyils.usher.pojo.cqe.SpaceCreateCommand;
import top.uhyils.usher.pojo.cqe.UpdateSpaceInfoCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.AiSpaceProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.AiSpaceService;
import top.uhyils.usher.service.BaseDoService;

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
    public Boolean removeSubSpace(IdCommand command) {
        return service.removeSubSpace(command);
    }

    @Override
    public Boolean changeSubSpace(ChangeSubSpaceCommand command) {
        return service.changeSubSpace(command);
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
    public AiSubspaceDTO findSubSpaceById(IdQuery query) {
        return service.findSubSpaceById(query);
    }


    @Override
    protected BaseDoService<AiSpaceDTO> getService() {
        return service;
    }

}

