package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OrderBaseNodeFieldDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import team.opentech.usher.pojo.entity.OrderBaseNodeField;
import org.mapstruct.Mapper;

/**
 * 工单节点属性样例表(OrderBaseNodeField)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分00秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderBaseNodeFieldAssembler extends AbstractAssembler<OrderBaseNodeFieldDO, OrderBaseNodeField, OrderBaseNodeFieldDTO> {

}
