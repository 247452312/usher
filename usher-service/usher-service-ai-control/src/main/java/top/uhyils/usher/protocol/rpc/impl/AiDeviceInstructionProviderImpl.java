package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.AiDeviceInstructionDTO;
import top.uhyils.usher.pojo.DTO.AiSubspaceDTO;
import top.uhyils.usher.pojo.cqe.CopyInstructionsByDeviceIdCommand;
import top.uhyils.usher.pojo.cqe.CreateDeviceInstructionCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.AiDeviceInstructionProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.AiDeviceInstructionService;
import top.uhyils.usher.service.BaseDoService;

/**
 * 设备指令表(AiDeviceInstruction)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@RpcService
public class AiDeviceInstructionProviderImpl extends BaseDefaultProvider<AiDeviceInstructionDTO> implements AiDeviceInstructionProvider {


    @Autowired
    private AiDeviceInstructionService service;

    @Override
    public Boolean createDeviceInstruction(CreateDeviceInstructionCommand command) {
        return service.createDeviceInstruction(command);
    }

    @Override
    public Boolean removeDeviceInstruction(IdCommand command) {
        return service.removeDeviceInstruction(command);
    }

    @Override
    public List<AiDeviceInstructionDTO> findDeviceInstructionById(IdCommand command) {
        return service.findDeviceInstructionById(command);
    }

    @Override
    public Boolean copyInstructionsByDeviceId(CopyInstructionsByDeviceIdCommand command) {
        return service.copyInstructionsByDeviceId(command);
    }

    @Override
    public List<AiSubspaceDTO> findAllSubSpaceInSpaceByDeviceId(IdQuery query) {
        return service.findAllSubSpaceInSpaceByDeviceId(query);
    }

    @Override
    public Object executeInstruction(StringCommand command) {
        return service.executeInstruction(command);
    }


    @Override
    protected BaseDoService<AiDeviceInstructionDTO> getService() {
        return service;
    }

}

