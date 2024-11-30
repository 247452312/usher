package top.uhyils.usher.assembler;


import java.util.HashMap;
import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.enums.OrderNodeTypeEnum;
import top.uhyils.usher.pojo.DO.OrderInfoDO;
import top.uhyils.usher.pojo.DO.OrderNodeDO;
import top.uhyils.usher.pojo.DTO.InitOrderDTO;
import top.uhyils.usher.pojo.DTO.OrderBaseInfoDTO;
import top.uhyils.usher.pojo.DTO.OrderInfoDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.pojo.entity.OrderInfo;
import top.uhyils.usher.pojo.entity.OrderNode;
import top.uhyils.usher.util.Asserts;

/**
 * 工单基础信息样例表(OrderInfo)表 entity,DO,DTO转换工具
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分13秒
 */
@Mapper(componentModel = "spring")
public abstract class OrderInfoAssembler extends AbstractAssembler<OrderInfoDO, OrderInfo, OrderInfoDTO> {

    @Autowired
    private OrderNodeAssembler nodeAssembler;

    @Autowired
    private OrderNodeFieldAssembler nodeFieldAssembler;


    @Override
    public OrderInfo toEntity(OrderInfoDTO dto) {
        OrderInfo orderInfo = new OrderInfo(toDo(dto));
        List<OrderNodeDTO> nodes = dto.getNodes();
        List<OrderNode> orderNodes = nodeAssembler.listDTOToEntity(nodes);
        orderInfo.forceFillNodes(orderNodes);
        return orderInfo;
    }

    public abstract OrderInfoDTO baseInfoDTOToInfoDTO(OrderBaseInfoDTO order);

    public InitOrderDTO toInitOrderDTO(OrderInfo orderInfo) {
        OrderInfoDO orderInfoDO = orderInfo.toData().orElseThrow(Asserts::throwOptionalException);
        List<OrderNode> nodes = orderInfo.nodes();

        // 创建之后的首节点的属性(返回给前台用)
        List<OrderNodeFieldDTO> orderNodeField = null;
        // 每个节点的处理人(返回给前台用)
        HashMap<Long, Long> dealUserIds = new HashMap<>(nodes.size());
        // 每个节点的抄送人(返回给前台用)
        HashMap<Long, Long> noticeUserIds = new HashMap<>(nodes.size());

        for (OrderNode node : nodes) {
            OrderNodeDO orderNodeDO = node.toData().orElseThrow(Asserts::throwOptionalException);
            Integer type = orderNodeDO.getType();
            OrderNodeTypeEnum orderNodeType = OrderNodeTypeEnum.parse(type);
            if (orderNodeType == OrderNodeTypeEnum.START) {
                orderNodeField = nodeFieldAssembler.listEntityToDTO(node.fields());
            }

            dealUserIds.put(orderNodeDO.getId(), orderNodeDO.getRunDealUserId());
            noticeUserIds.put(orderNodeDO.getId(), orderNodeDO.getNoticeUserId());
        }

        return InitOrderDTO.build(orderInfo.getUnique().orElseThrow(Asserts::throwOptionalException), orderNodeField, orderInfoDO.getMonitorUserId(), dealUserIds, noticeUserIds);
    }
}

