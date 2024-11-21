package team.opentech.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.pojo.cqe.CopyInstructionsByDeviceIdCommand;
import team.opentech.usher.pojo.cqe.CreateDeviceInstructionCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.AiDeviceInstructionProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiDeviceInstructionService;
import team.opentech.usher.service.BaseDoService;

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
    public Boolean executeInstruction(IdCommand command) {
        return service.executeInstruction(command);
    }


    @Override
    protected BaseDoService<AiDeviceInstructionDTO> getService() {
        return service;
    }

}

