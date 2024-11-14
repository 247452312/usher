package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.ProviderInterfaceParamDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceParamDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceParam;

/**
 * 接口参数表(ProviderInterfaceParam)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class ProviderInterfaceParamAssembler extends AbstractAssembler<ProviderInterfaceParamDO, ProviderInterfaceParam, ProviderInterfaceParamDTO> {

}
