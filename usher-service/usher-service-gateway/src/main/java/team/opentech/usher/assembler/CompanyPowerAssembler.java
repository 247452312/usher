package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.CompanyPowerDO;
import team.opentech.usher.pojo.DTO.CompanyPowerDTO;
import team.opentech.usher.pojo.entity.CompanyPower;

/**
 * 厂商接口调用权限表(CompanyPower)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CompanyPowerAssembler extends AbstractAssembler<CompanyPowerDO, CompanyPower, CompanyPowerDTO> {

}
