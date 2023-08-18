package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ProviderInterfaceAssembler;
import team.opentech.usher.pojo.DO.ProviderInterfaceDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceDTO;
import team.opentech.usher.pojo.entity.ProviderInterface;
import team.opentech.usher.repository.ProviderInterfaceRepository;
import team.opentech.usher.service.ProviderInterfaceService;
import org.springframework.stereotype.Service;

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
