package top.uhyils.usher.assembler;


import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.builder.OrderApplyBuilder;
import top.uhyils.usher.pojo.DO.OrderInfoDO;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.DTO.OrderApplyDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeResultTypeDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeRouteDTO;
import top.uhyils.usher.pojo.entity.OrderApply;
import top.uhyils.usher.pojo.entity.OrderInfo;
import top.uhyils.usher.pojo.entity.OrderNode;
import top.uhyils.usher.util.Asserts;

/**
 * 工单节点样例表(OrderNode)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分20秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderNodeAssembler extends AbstractAssembler<OrderNodeDO, OrderNode, OrderNodeDTO> {

    @Autowired
    private OrderNodeFieldAssembler fieldAssembler;

    @Autowired
    private OrderNodeRouteAssembler routeAssembler;

    @Autowired
    private OrderNodeResultTypeAssembler resultTypeAssembler;

    @Autowired
    private OrderApplyAssembler applyAssembler;

    @Override
    public OrderNode toEntity(OrderNodeDTO dto) {
        OrderNode orderNode = new OrderNode(toDo(dto));
        List<OrderNodeFieldDTO> fields = dto.getFields();
        List<OrderNodeResultTypeDTO> resultTypes = dto.getResultTypes();
        List<OrderNodeRouteDTO> routes = dto.getRoutes();
        orderNode.forceFillInfo(fieldAssembler.listDTOToEntity(fields), resultTypeAssembler.listDTOToEntity(resultTypes), routeAssembler.listDTOToEntity(routes));
        return orderNode;
    }

    public OrderApply toApply(OrderNode orderNode, OrderInfo baseInfo) {
        OrderApplyDTO orderApplyDTO = OrderApplyBuilder.buildTransApplyByOrderNode(toDTO(orderNode), baseInfo.toData().map(OrderInfoDO::getMonitorUserId).orElseThrow(Asserts::throwOptionalException));
        return applyAssembler.toEntity(orderApplyDTO);
    }
}

