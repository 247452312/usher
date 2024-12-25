package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import top.uhyils.usher.protocol.rpc.ProviderInterfaceRpcProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ProviderInterfaceRpcService;

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

