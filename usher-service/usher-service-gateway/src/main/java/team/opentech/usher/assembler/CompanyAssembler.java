package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.CompanyDO;
import team.opentech.usher.pojo.DTO.CompanyDTO;
import team.opentech.usher.pojo.entity.Company;
import org.mapstruct.Mapper;

/**
 * 厂商表(Company)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CompanyAssembler extends AbstractAssembler<CompanyDO, Company, CompanyDTO> {

}
