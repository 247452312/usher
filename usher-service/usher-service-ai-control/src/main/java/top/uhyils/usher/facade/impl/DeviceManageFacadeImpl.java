package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.DeviceManageFacade;
import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.protocol.rpc.DeviceManageProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月06日 16时49分
 */
@Facade
public class DeviceManageFacadeImpl implements DeviceManageFacade {

    @RpcReference
    private DeviceManageProvider deviceManageProvider;

    @Override
    public Object executeInstruction(String deviceInstructionNo, String context, String uniqueMark) {
        ExecuteInstructionCommand command = new ExecuteInstructionCommand();
        command.setDeviceInstructionNo(deviceInstructionNo);
        command.setContext(context);
        command.setUniqueMark(uniqueMark);
        return deviceManageProvider.executeInstruction(command);
    }
}
