package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ProviderInterfaceRpcAssembler;
import top.uhyils.usher.pojo.DO.ProviderInterfaceRpcDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceRpc;
import top.uhyils.usher.repository.ProviderInterfaceRpcRepository;
import top.uhyils.usher.service.ProviderInterfaceRpcService;

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
