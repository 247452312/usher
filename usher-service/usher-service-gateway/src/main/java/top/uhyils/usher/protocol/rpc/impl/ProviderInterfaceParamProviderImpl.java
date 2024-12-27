package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceParamDTO;
import top.uhyils.usher.protocol.rpc.ProviderInterfaceParamProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ProviderInterfaceParamService;

/**
 * 接口参数表(ProviderInterfaceParam)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class ProviderInterfaceParamProviderImpl extends BaseDefaultProvider<ProviderInterfaceParamDTO> implements ProviderInterfaceParamProvider {


    @Autowired
    private ProviderInterfaceParamService service;


    @Override
    protected BaseDoService<ProviderInterfaceParamDTO> getService() {
        return service;
    }

}

