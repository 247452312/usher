package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ProviderInterfaceHttpAssembler;
import top.uhyils.usher.pojo.DO.ProviderInterfaceHttpDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceHttp;
import top.uhyils.usher.repository.ProviderInterfaceHttpRepository;
import top.uhyils.usher.service.ProviderInterfaceHttpService;

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
