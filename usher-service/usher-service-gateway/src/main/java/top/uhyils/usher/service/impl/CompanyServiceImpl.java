package top.uhyils.usher.service.impl;

import org.springframework.stereotype.Service;
import top.uhyils.usher.annotation.ReadWriteMark;
import top.uhyils.usher.assembler.CompanyAssembler;
import top.uhyils.usher.pojo.DO.CompanyDO;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.entity.Company;
import top.uhyils.usher.repository.CompanyRepository;
import top.uhyils.usher.service.CompanyService;

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
