package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OrderBaseNodeDO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeDTO;
import team.opentech.usher.pojo.entity.OrderBaseNode;
import team.opentech.usher.pojo.entity.OrderBaseNodeField;
import team.opentech.usher.pojo.entity.OrderBaseNodeResultType;
import team.opentech.usher.pojo.entity.OrderBaseNodeRoute;
import team.opentech.usher.util.Asserts;
import java.util.List;
import org.mapstruct.Mapper;
import javax.annotation.Resource;

/**
 * 工单节点样例表(OrderBaseNode)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分57秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderBaseNodeAssembler extends AbstractAssembler<OrderBaseNodeDO, OrderBaseNode, OrderBaseNodeDTO> {

    @Resource
    private OrderBaseNodeFieldAssembler fieldAssembler;

    @Resource
    private OrderBaseNodeResultTypeAssembler resultTypeAssembler;

    @Resource
    private OrderBaseNodeRouteAssembler routeAssembler;


    @Override
    public OrderBaseNode toEntity(OrderBaseNodeDTO dto) {
        OrderBaseNode orderBaseNode = new OrderBaseNode(toDo(dto));
        List<OrderBaseNodeField> fields = fieldAssembler.listDTOToEntity(dto.getFields());
        orderBaseNode.forceFillFields(fields);
        List<OrderBaseNodeRoute> routes = routeAssembler.listDTOToEntity(dto.getRoutes());
        orderBaseNode.forceFillRoutes(routes);
        List<OrderBaseNodeResultType> resultTypes = resultTypeAssembler.listDTOToEntity(dto.getResultTypes());
        orderBaseNode.forceFillResultTypes(resultTypes);
        return orderBaseNode;
    }

    @Override
    public OrderBaseNodeDTO toDTO(OrderBaseNode entity) {
        OrderBaseNodeDTO orderBaseNodeDTO = toDTO(entity.toData().orElseThrow(Asserts::throwOptionalException));
        orderBaseNodeDTO.setFields(fieldAssembler.listEntityToDTO(entity.fields()));
        orderBaseNodeDTO.setRoutes(routeAssembler.listEntityToDTO(entity.routes()));
        orderBaseNodeDTO.setResultTypes(resultTypeAssembler.listEntityToDTO(entity.resultTypes()));
        return orderBaseNodeDTO;
    }
}

