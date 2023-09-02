package team.opentech.usher.service.impl;

import org.springframework.stereotype.Service;
import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.CompanyPowerAssembler;
import team.opentech.usher.pojo.DO.CompanyPowerDO;
import team.opentech.usher.pojo.DTO.CompanyPowerDTO;
import team.opentech.usher.pojo.entity.CompanyPower;
import team.opentech.usher.repository.CompanyPowerRepository;
import team.opentech.usher.service.CompanyPowerService;

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
