package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.BlackListDO;
import team.opentech.usher.pojo.DTO.BlackListDTO;
import team.opentech.usher.pojo.entity.BlackList;
import org.mapstruct.Mapper;

/**
 * 黑名单(BlackList)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分52秒
 */
@Mapper(componentModel = "spring")
public abstract class BlackListAssembler extends AbstractAssembler<BlackListDO, BlackList, BlackListDTO> {

}

