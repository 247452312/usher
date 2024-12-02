package top.uhyils.usher.protocol.rpc.impl;

import javax.annotation.Resource;
import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.protocol.rpc.DeviceManageProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.DeviceManageService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时21分
 */
@RpcService
public class DeviceManageProviderImpl implements DeviceManageProvider {

    @Resource
    private DeviceManageService service;

    @Override
    public Boolean executeInstruction(ExecuteInstructionCommand command) {
        return service.executeInstruction(command);
    }
}
