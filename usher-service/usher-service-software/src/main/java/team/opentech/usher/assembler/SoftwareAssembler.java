package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.SoftwareDO;
import team.opentech.usher.pojo.DTO.SoftwareDTO;
import team.opentech.usher.pojo.entity.Software;
import org.mapstruct.Mapper;

/**
 * 中间件表(Software)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分21秒
 */
@Mapper(componentModel = "spring")
public abstract class SoftwareAssembler extends AbstractAssembler<SoftwareDO, Software, SoftwareDTO> {

}
