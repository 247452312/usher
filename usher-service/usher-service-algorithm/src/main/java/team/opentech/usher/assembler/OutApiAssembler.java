package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OutApiDO;
import team.opentech.usher.pojo.DTO.OutApiDTO;
import team.opentech.usher.pojo.entity.OutApi;
import org.mapstruct.Mapper;

/**
 * 开放api(OutApi)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月09日 20时58分10秒
 */
@Mapper(componentModel = "spring")
public abstract class OutApiAssembler extends AbstractAssembler<OutApiDO, OutApi, OutApiDTO> {

}

