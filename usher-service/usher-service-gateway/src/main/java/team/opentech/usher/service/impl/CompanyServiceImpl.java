package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.CompanyAssembler;
import team.opentech.usher.pojo.DO.CompanyDO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.entity.Company;
import team.opentech.usher.repository.CompanyRepository;
import team.opentech.usher.service.CompanyService;
import org.springframework.stereotype.Service;

/**
 * 厂商表(Company)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Service
@ReadWriteMark(tables = {"sys_company"})
public class CompanyServiceImpl extends AbstractDoService<CompanyDO, Company, CompanyDTO, CompanyRepository, CompanyAssembler> implements CompanyService {

    public CompanyServiceImpl(CompanyAssembler assembler, CompanyRepository repository) {
        super(assembler, repository);
    }


}
