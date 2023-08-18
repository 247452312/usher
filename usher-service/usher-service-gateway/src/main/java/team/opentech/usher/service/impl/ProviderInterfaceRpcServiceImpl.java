package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ProviderInterfaceRpcAssembler;
import team.opentech.usher.pojo.DO.ProviderInterfaceRpcDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceRpc;
import team.opentech.usher.repository.ProviderInterfaceRpcRepository;
import team.opentech.usher.service.ProviderInterfaceRpcService;
import org.springframework.stereotype.Service;

/**
 * http接口子表(ProviderInterfaceRpc)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_provider_interface_rpc"})
public class ProviderInterfaceRpcServiceImpl extends AbstractDoService<ProviderInterfaceRpcDO, ProviderInterfaceRpc, ProviderInterfaceRpcDTO, ProviderInterfaceRpcRepository, ProviderInterfaceRpcAssembler> implements ProviderInterfaceRpcService {

    public ProviderInterfaceRpcServiceImpl(ProviderInterfaceRpcAssembler assembler, ProviderInterfaceRpcRepository repository) {
        super(assembler, repository);
    }


}
