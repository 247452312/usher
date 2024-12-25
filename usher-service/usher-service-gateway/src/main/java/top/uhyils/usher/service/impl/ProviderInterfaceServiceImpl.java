package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ProviderInterfaceAssembler;
import top.uhyils.usher.pojo.DO.ProviderInterfaceDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceDTO;
import top.uhyils.usher.pojo.entity.ProviderInterface;
import top.uhyils.usher.repository.ProviderInterfaceRepository;
import top.uhyils.usher.service.ProviderInterfaceService;

/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_provider_interface"})
public class ProviderInterfaceServiceImpl extends AbstractDoService<ProviderInterfaceDO, ProviderInterface, ProviderInterfaceDTO, ProviderInterfaceRepository, ProviderInterfaceAssembler> implements ProviderInterfaceService {

    public ProviderInterfaceServiceImpl(ProviderInterfaceAssembler assembler, ProviderInterfaceRepository repository) {
        super(assembler, repository);
    }


}
