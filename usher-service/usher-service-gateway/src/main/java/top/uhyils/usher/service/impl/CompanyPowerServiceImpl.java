package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.CompanyPowerAssembler;
import top.uhyils.usher.pojo.DO.CompanyPowerDO;
import top.uhyils.usher.pojo.DTO.CompanyPowerDTO;
import top.uhyils.usher.pojo.entity.CompanyPower;
import top.uhyils.usher.repository.CompanyPowerRepository;
import top.uhyils.usher.service.CompanyPowerService;

/**
 * 厂商接口调用权限表(CompanyPower)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_company_power"})
public class CompanyPowerServiceImpl extends AbstractDoService<CompanyPowerDO, CompanyPower, CompanyPowerDTO, CompanyPowerRepository, CompanyPowerAssembler> implements CompanyPowerService {

    public CompanyPowerServiceImpl(CompanyPowerAssembler assembler, CompanyPowerRepository repository) {
        super(assembler, repository);
    }


}
