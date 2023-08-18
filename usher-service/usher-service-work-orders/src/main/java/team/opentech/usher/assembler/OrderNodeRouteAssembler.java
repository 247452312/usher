package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OrderNodeRouteDO;
import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderNodeRoute;
import org.mapstruct.Mapper;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分33秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderNodeRouteAssembler extends AbstractAssembler<OrderNodeRouteDO, OrderNodeRoute, OrderNodeRouteDTO> {

}
