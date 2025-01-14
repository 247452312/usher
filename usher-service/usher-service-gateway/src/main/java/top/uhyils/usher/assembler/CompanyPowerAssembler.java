package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.CompanyPowerDO;
import top.uhyils.usher.pojo.DTO.CompanyPowerDTO;
import top.uhyils.usher.pojo.entity.CompanyPower;

/**
 * 厂商接口调用权限表(CompanyPower)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class CompanyPowerAssembler extends AbstractAssembler<CompanyPowerDO, CompanyPower, CompanyPowerDTO> {

    public CompanyPower toEntity(Long nodeId, Long companyId, Integer status) {
        CompanyPowerDO data = new CompanyPowerDO();
        data.setCompanyId(companyId);
        data.setNodeId(nodeId);
        data.setStatus(status);
        return new CompanyPower(data);
    }
}
