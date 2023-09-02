package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ProviderInterfaceHttpAssembler;
import team.opentech.usher.pojo.DO.ProviderInterfaceHttpDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceHttp;
import team.opentech.usher.repository.ProviderInterfaceHttpRepository;
import team.opentech.usher.service.ProviderInterfaceHttpService;

/**
 * http接口子表(ProviderInterfaceHttp)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_provider_interface_http"})
public class ProviderInterfaceHttpServiceImpl extends AbstractDoService<ProviderInterfaceHttpDO, ProviderInterfaceHttp, ProviderInterfaceHttpDTO, ProviderInterfaceHttpRepository, ProviderInterfaceHttpAssembler> implements ProviderInterfaceHttpService {

    public ProviderInterfaceHttpServiceImpl(ProviderInterfaceHttpAssembler assembler, ProviderInterfaceHttpRepository repository) {
        super(assembler, repository);
    }


}
