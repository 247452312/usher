package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import team.opentech.usher.protocol.rpc.ProviderInterfaceRpcProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ProviderInterfaceRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * http接口子表(ProviderInterfaceRpc)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class ProviderInterfaceRpcProviderImpl extends BaseDefaultProvider<ProviderInterfaceRpcDTO> implements ProviderInterfaceRpcProvider {


    @Autowired
    private ProviderInterfaceRpcService service;


    @Override
    protected BaseDoService<ProviderInterfaceRpcDTO> getService() {
        return service;
    }

}

