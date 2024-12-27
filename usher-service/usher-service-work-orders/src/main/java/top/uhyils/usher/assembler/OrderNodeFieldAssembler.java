package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.OrderNodeFieldDO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderNodeField;

/**
 * 工单节点属性样例表(OrderNodeField)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分24秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderNodeFieldAssembler extends AbstractAssembler<OrderNodeFieldDO, OrderNodeField, OrderNodeFieldDTO> {

}
