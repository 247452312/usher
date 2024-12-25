package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.ProviderInterfaceHttpDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceHttpDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceHttp;

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
