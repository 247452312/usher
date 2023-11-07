package team.opentech.usher.assembler;


import team.opentech.usher.pojo.DO.OrderBaseInfoDO;
import team.opentech.usher.pojo.DTO.OrderBaseInfoDTO;
import team.opentech.usher.pojo.DTO.OrderBaseNodeDTO;
import team.opentech.usher.pojo.entity.OrderBaseInfo;
import team.opentech.usher.pojo.entity.OrderBaseNode;
import team.opentech.usher.util.Asserts;
import java.util.List;
import org.mapstruct.Mapper;
import javax.annotation.Resource;

/**
 * 工单基础信息样例表(OrderBaseInfo)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分53秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderBaseInfoAssembler extends AbstractAssembler<OrderBaseInfoDO, OrderBaseInfo, OrderBaseInfoDTO> {

    @Resource
    private OrderBaseNodeAssembler nodeAssembler;

    @Override
    public OrderBaseInfo toEntity(OrderBaseInfoDO dO) {
        return new OrderBaseInfo(dO);
    }

    @Override
    public OrderBaseInfo toEntity(OrderBaseInfoDTO dto) {
        OrderBaseInfo orderBaseInfo = new OrderBaseInfo(toDo(dto));
        List<OrderBaseNodeDTO> nodes = dto.getNodes();
        List<OrderBaseNode> orderBaseNodes = nodeAssembler.listDTOToEntity(nodes);
        orderBaseInfo.forceFillNode(orderBaseNodes);
        return orderBaseInfo;
    }

    @Override
    public OrderBaseInfoDTO toDTO(OrderBaseInfo entity) {
        OrderBaseInfoDTO orderBaseInfoDTO = toDTO(entity.toData().orElseThrow(Asserts::throwOptionalException));
        List<OrderBaseNode> nodes = entity.nodes();
        List<OrderBaseNodeDTO> orderBaseNodeDTOList = nodeAssembler.listEntityToDTO(nodes);
        orderBaseInfoDTO.setNodes(orderBaseNodeDTOList);
        return orderBaseInfoDTO;
    }
}

