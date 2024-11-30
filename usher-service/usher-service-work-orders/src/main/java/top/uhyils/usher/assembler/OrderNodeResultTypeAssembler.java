package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.OrderNodeResultTypeDO;
import top.uhyils.usher.pojo.DTO.OrderNodeResultTypeDTO;
import top.uhyils.usher.pojo.entity.OrderNodeResultType;

/**
 * 工单节点处理结果样例表(OrderNodeResultType)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分30秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderNodeResultTypeAssembler extends AbstractAssembler<OrderNodeResultTypeDO, OrderNodeResultType, OrderNodeResultTypeDTO> {

}
