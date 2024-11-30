package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.ProviderInterfaceMysqlAssembler;
import top.uhyils.usher.pojo.DO.ProviderInterfaceMysqlDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceMysql;
import top.uhyils.usher.repository.ProviderInterfaceMysqlRepository;
import top.uhyils.usher.service.ProviderInterfaceMysqlService;

/**
 * mysql接口子表(ProviderInterfaceMysql)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_provider_interface_mysql"})
public class ProviderInterfaceMysqlServiceImpl extends AbstractDoService<ProviderInterfaceMysqlDO, ProviderInterfaceMysql, ProviderInterfaceMysqlDTO, ProviderInterfaceMysqlRepository, ProviderInterfaceMysqlAssembler> implements ProviderInterfaceMysqlService {

    public ProviderInterfaceMysqlServiceImpl(ProviderInterfaceMysqlAssembler assembler, ProviderInterfaceMysqlRepository repository) {
        super(assembler, repository);
    }


}
