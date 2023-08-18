package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.MenuDO;
import team.opentech.usher.pojo.DTO.MenuDTO;
import team.opentech.usher.pojo.entity.Menu;
import org.mapstruct.Mapper;

/**
 * 菜单(Menu)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分45秒
 */
@Mapper(componentModel = "spring")
public abstract class MenuAssembler extends AbstractAssembler<MenuDO, Menu, MenuDTO> {

}
