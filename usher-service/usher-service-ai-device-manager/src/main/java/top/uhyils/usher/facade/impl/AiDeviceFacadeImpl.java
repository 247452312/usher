package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.facade.AiDeviceFacade;
import top.uhyils.usher.pojo.DTO.AiDeviceAndRealTimeDTO;
import top.uhyils.usher.pojo.cqe.query.StringQuery;
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
    public AiDeviceAndRealTimeDTO findByUniqueMark(String uniqueMark) {
        return aiDeviceProvider.findDeviceAndRealTimeByUniqueMark(StringQuery.build(uniqueMark));
    }
}
