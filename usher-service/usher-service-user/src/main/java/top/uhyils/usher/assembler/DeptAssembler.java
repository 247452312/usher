package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.DeptDO;
import top.uhyils.usher.pojo.DTO.DeptDTO;
import top.uhyils.usher.pojo.entity.Dept;

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
