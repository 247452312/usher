package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ProviderInterfaceParamAssembler;
import team.opentech.usher.pojo.DO.ProviderInterfaceParamDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceParamDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceParam;
import team.opentech.usher.repository.ProviderInterfaceParamRepository;
import team.opentech.usher.service.ProviderInterfaceParamService;

/**
 * 接口参数表(ProviderInterfaceParam)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_provider_interface_param"})
public class ProviderInterfaceParamServiceImpl extends AbstractDoService<ProviderInterfaceParamDO, ProviderInterfaceParam, ProviderInterfaceParamDTO, ProviderInterfaceParamRepository, ProviderInterfaceParamAssembler> implements ProviderInterfaceParamService {

    public ProviderInterfaceParamServiceImpl(ProviderInterfaceParamAssembler assembler, ProviderInterfaceParamRepository repository) {
        super(assembler, repository);
    }


}
