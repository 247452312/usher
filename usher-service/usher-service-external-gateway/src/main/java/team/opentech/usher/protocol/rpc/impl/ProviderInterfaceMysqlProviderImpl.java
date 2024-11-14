package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import team.opentech.usher.protocol.rpc.ProviderInterfaceMysqlProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ProviderInterfaceMysqlService;

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

