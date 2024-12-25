package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import top.uhyils.usher.protocol.rpc.ProviderInterfaceMysqlProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ProviderInterfaceMysqlService;

/**
 * mysql接口子表(ProviderInterfaceMysql)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@RpcService
public class ProviderInterfaceMysqlProviderImpl extends BaseDefaultProvider<ProviderInterfaceMysqlDTO> implements ProviderInterfaceMysqlProvider {


    @Autowired
    private ProviderInterfaceMysqlService service;


    @Override
    protected BaseDoService<ProviderInterfaceMysqlDTO> getService() {
        return service;
    }

}

