package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiDeviceInstructionDTO;
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
    protected BaseDoService<AiDeviceInstructionDTO> getService() {
        return service;
    }

}

