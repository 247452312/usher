package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.ParamDO;
import top.uhyils.usher.pojo.DTO.ParamDTO;
import top.uhyils.usher.pojo.entity.Param;

/**
 * 系统参数表(Param)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2022年06月17日 15时53分
 */
@Mapper(componentModel = "spring")
public abstract class ParamAssembler extends AbstractAssembler<ParamDO, Param, ParamDTO> {

}
