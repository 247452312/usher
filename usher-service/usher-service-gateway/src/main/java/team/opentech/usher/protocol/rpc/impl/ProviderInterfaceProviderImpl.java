package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ProviderInterfaceDTO;
import team.opentech.usher.protocol.rpc.ProviderInterfaceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ProviderInterfaceService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class ProviderInterfaceProviderImpl extends BaseDefaultProvider<ProviderInterfaceDTO> implements ProviderInterfaceProvider {


    @Autowired
    private ProviderInterfaceService service;


    @Override
    protected BaseDoService<ProviderInterfaceDTO> getService() {
        return service;
    }

}

