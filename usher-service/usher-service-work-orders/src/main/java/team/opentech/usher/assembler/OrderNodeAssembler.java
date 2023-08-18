package team.opentech.usher.assembler;


import team.opentech.usher.builder.OrderApplyBuilder;
import team.opentech.usher.pojo.DO.OrderInfoDO;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DTO.OrderApplyDTO;
import team.opentech.usher.pojo.DTO.OrderNodeDTO;
import team.opentech.usher.pojo.DTO.OrderNodeFieldDTO;
import team.opentech.usher.pojo.DTO.OrderNodeResultTypeDTO;
import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.pojo.entity.OrderApply;
import team.opentech.usher.pojo.entity.OrderInfo;
import team.opentech.usher.pojo.entity.OrderNode;
import team.opentech.usher.util.Asserts;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

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

