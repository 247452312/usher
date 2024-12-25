package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.ProviderInterfaceMysqlDO;
import top.uhyils.usher.pojo.DTO.ProviderInterfaceMysqlDTO;
import top.uhyils.usher.pojo.entity.ProviderInterfaceMysql;

/**
 * mysql接口子表(ProviderInterfaceMysql)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年09月09日 15时45分
 */
@Mapper(componentModel = "spring")
public abstract class ProviderInterfaceMysqlAssembler extends AbstractAssembler<ProviderInterfaceMysqlDO, ProviderInterfaceMysql, ProviderInterfaceMysqlDTO> {

}
