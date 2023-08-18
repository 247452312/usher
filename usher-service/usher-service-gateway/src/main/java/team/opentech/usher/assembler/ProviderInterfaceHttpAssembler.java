package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.ProviderInterfaceHttpDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceHttp;
import org.mapstruct.Mapper;

/**
 * http接口子表(ProviderInterfaceHttp)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class ProviderInterfaceHttpAssembler extends AbstractAssembler<ProviderInterfaceHttpDO, ProviderInterfaceHttp, ProviderInterfaceHttpDTO> {

}
