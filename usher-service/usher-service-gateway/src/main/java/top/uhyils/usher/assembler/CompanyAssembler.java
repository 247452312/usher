package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.CompanyDO;
import top.uhyils.usher.pojo.DTO.CompanyDTO;
import top.uhyils.usher.pojo.cqe.CompanyCreateCommand;
import top.uhyils.usher.pojo.entity.Company;

/**
 * 厂商表(Company)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CompanyAssembler extends AbstractAssembler<CompanyDO, Company, CompanyDTO> {


    public Company toEntity(CompanyCreateCommand command) {
        CompanyDO data = new CompanyDO();
        data.setName(command.getName());
        data.setPersonName(command.getPersonName());
        data.setPersonPhone(command.getPersonPhone());
        data.setAk(command.getAk());
        data.setSk(command.getSk());
        return new Company(data);
    }
}
