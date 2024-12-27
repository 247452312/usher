package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.OrderApplyDO;
import top.uhyils.usher.pojo.DTO.OrderApplyDTO;
import top.uhyils.usher.pojo.entity.OrderApply;

/**
 * (OrderApply)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分49秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderApplyAssembler extends AbstractAssembler<OrderApplyDO, OrderApply, OrderApplyDTO> {

}
