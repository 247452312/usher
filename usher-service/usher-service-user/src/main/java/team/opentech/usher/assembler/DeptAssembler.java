package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.DeptDO;
import team.opentech.usher.pojo.DTO.DeptDTO;
import team.opentech.usher.pojo.entity.Dept;
import org.mapstruct.Mapper;

/**
 * 部门(Dept)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分21秒
 */
@Mapper(componentModel = "spring")
public abstract class DeptAssembler extends AbstractAssembler<DeptDO, Dept, DeptDTO> {

}
