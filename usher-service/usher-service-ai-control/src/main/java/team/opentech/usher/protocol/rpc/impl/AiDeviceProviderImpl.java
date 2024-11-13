package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiDeviceDTO;
import team.opentech.usher.protocol.rpc.AiDeviceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiDeviceService;
import team.opentech.usher.service.BaseDoService;

/**
 * 设备表(AiDevice)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 20时55分
 */
@RpcService
public class AiDeviceProviderImpl extends BaseDefaultProvider<AiDeviceDTO> implements AiDeviceProvider {


    @Autowired
    private AiDeviceService service;


    @Override
    protected BaseDoService<AiDeviceDTO> getService() {
        return service;
    }

}

