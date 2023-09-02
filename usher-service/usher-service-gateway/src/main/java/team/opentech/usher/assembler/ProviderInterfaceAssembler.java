package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.ProviderInterfaceDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceDTO;
import team.opentech.usher.pojo.entity.ProviderInterface;

/**
 * 接口表,提供方提供的调用方式以及url(ProviderInterface)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class ProviderInterfaceAssembler extends AbstractAssembler<ProviderInterfaceDO, ProviderInterface, ProviderInterfaceDTO> {

}
