package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.PowerDO;
import team.opentech.usher.pojo.DTO.PowerDTO;
import team.opentech.usher.pojo.entity.Power;
import org.mapstruct.Mapper;

/**
 * 权限(Power)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分50秒
 */
@Mapper(componentModel = "spring")
public abstract class PowerAssembler extends AbstractAssembler<PowerDO, Power, PowerDTO> {

}
