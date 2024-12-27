package top.uhyils.usher.assembler;


import org.mapstruct.Mapper;
import top.uhyils.usher.pojo.DO.OrderBaseNodeRouteDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNodeRoute;

/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分07秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderBaseNodeRouteAssembler extends AbstractAssembler<OrderBaseNodeRouteDO, OrderBaseNodeRoute, OrderBaseNodeRouteDTO> {

}
