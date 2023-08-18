package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.ProviderInterfaceMysqlAssembler;
import team.opentech.usher.pojo.DO.ProviderInterfaceMysqlDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceMysql;
import team.opentech.usher.repository.ProviderInterfaceMysqlRepository;
import team.opentech.usher.service.ProviderInterfaceMysqlService;
import org.springframework.stereotype.Service;

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
