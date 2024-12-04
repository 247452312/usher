package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceDTO;
import top.uhyils.usher.pojo.cqe.command.StringCommand;
import top.uhyils.usher.protocol.rpc.AiDeviceProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月03日 08时42分
 */
@Facade
public class AiDeviceFacadeImpl implements AiDeviceFacade {

    @RpcReference
    private AiDeviceProvider aiDeviceProvider;

    @Override
    public AiDeviceDTO findByUniqueMark(String uniqueMark) {
        return aiDeviceProvider.findByUniqueMark(StringCommand.build(uniqueMark));
    }
}
