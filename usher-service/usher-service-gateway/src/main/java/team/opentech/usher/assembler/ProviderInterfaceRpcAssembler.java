package team.opentech.usher.assembler;


import org.mapstruct.Mapper;
import team.opentech.usher.pojo.DO.ProviderInterfaceRpcDO;
import team.opentech.usher.pojo.DTO.ProviderInterfaceRpcDTO;
import team.opentech.usher.pojo.entity.ProviderInterfaceRpc;

/**
 * http接口子表(ProviderInterfaceRpc)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class ProviderInterfaceRpcAssembler extends AbstractAssembler<ProviderInterfaceRpcDO, ProviderInterfaceRpc, ProviderInterfaceRpcDTO> {

}
