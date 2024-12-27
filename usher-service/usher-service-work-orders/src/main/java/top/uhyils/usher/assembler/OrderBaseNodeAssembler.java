package top.uhyils.usher.assembler;


import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DO.OrderBaseNodeDO;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeDTO;
import top.uhyils.usher.pojo.entity.OrderBaseNode;
import top.uhyils.usher.pojo.entity.OrderBaseNodeField;
import top.uhyils.usher.pojo.entity.OrderBaseNodeResultType;
import top.uhyils.usher.pojo.entity.OrderBaseNodeRoute;
import top.uhyils.usher.util.Asserts;

/**
 * 工单节点样例表(OrderBaseNode)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分57秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderBaseNodeAssembler extends AbstractAssembler<OrderBaseNodeDO, OrderBaseNode, OrderBaseNodeDTO> {

    @Autowired
    private OrderBaseNodeFieldAssembler fieldAssembler;

    @Autowired
    private OrderBaseNodeResultTypeAssembler resultTypeAssembler;

    @Autowired
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

