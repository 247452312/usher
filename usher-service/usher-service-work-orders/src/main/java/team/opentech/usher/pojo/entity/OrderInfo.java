package team.opentech.usher.pojo.entity;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import team.opentech.usher.annotation.Default;
import team.opentech.usher.assembler.OrderInfoAssembler;
import team.opentech.usher.enums.OrderStatusEnum;
import team.opentech.usher.enums.PushTypeEnum;
import team.opentech.usher.facade.PushFacade;
import team.opentech.usher.pojo.DO.OrderInfoDO;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DTO.OrderInfoDTO;
import team.opentech.usher.pojo.IdMapping;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.repository.OrderInfoRepository;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.util.Asserts;
import team.opentech.usher.util.CollectionUtil;

/**
 * 工单基础信息样例表(OrderInfo)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时59分13秒
 */
public class OrderInfo extends AbstractDoEntity<OrderInfoDO> {

    private List<OrderNode> nodes;

    @Default
    public OrderInfo(OrderInfoDO data) {
        super(data);
    }

    public OrderInfo(Long id) {
        super(id, new OrderInfoDO());
    }

    public void forceFillNodes(List<OrderNode> nodes) {
        this.nodes = nodes;
    }

    public void saveSelf(OrderInfoRepository rep) {
        toData().orElseThrow(Asserts::throwOptionalException).setId(null);
        this.setUnique(null);
        this.setUnique(rep.save(this));
        // 修改node的OrderId
        changeNodesOrderId();
    }

    public List<OrderNode> nodes() {
        return nodes;
    }

    public void saveNode(OrderNodeRepository nodeRepository, OrderNodeFieldRepository fieldRepository, OrderNodeRouteRepository routeRepository, OrderNodeResultTypeRepository resultTypeRepository) {
        if (CollectionUtil.isEmpty(nodes)) {
            return;
        }
        Map<Long, Long> idMappings = new HashMap<>(this.nodes.size());
        for (OrderNode node : this.nodes) {
            IdMapping idMapping = node.saveSelf(nodeRepository);
            idMappings.put(idMapping.getOldId(), idMapping.getNewId());
        }

        for (OrderNode node : nodes) {
            node.changeAndSaveFieldNodeId(idMappings, fieldRepository);
            node.changeAndSaveResultTypeAndRoute(idMappings, routeRepository, resultTypeRepository);
        }

    }

    public void compareAndSave(OrderInfoRepository rep, Long monitorUserId) {
        OrderInfoDO orderInfoDO = toData().orElseThrow(Asserts::throwOptionalException);
        Long selfMonitor = orderInfoDO.getMonitorUserId();
        if (!Objects.equals(selfMonitor, monitorUserId)) {
            orderInfoDO.setMonitorUserId(monitorUserId);
            this.onUpdate();
            rep.save(this);
        }

    }

    public void changeOrderStatus(OrderInfoRepository rep, OrderStatusEnum code) {
        Integer status = data.getStatus();
        OrderStatusEnum statusEnum = OrderStatusEnum.parse(status);
        Asserts.assertTrue(code != statusEnum, MessageFormat.format("工单状态为:{0} 要修改成:{1},状态相同不能更改", statusEnum.getName(), code.getName()));
        data.setStatus(code.getCode());
        onUpdate();
        rep.save(this);
    }

    public void noticeMonitor(OrderInfoAssembler assem, PushFacade pushFacade, PushTypeEnum pushType, OrderStatusEnum targetStatus) {
        OrderInfoDTO orderInfoDTO = assem.toDTO(this);
        Boolean pushResult = pushFacade.pushMsg(orderInfoDTO, data.getMonitorUserId(), targetStatus, pushType);
        Asserts.assertTrue(pushResult, "推送失败");

    }

    /**
     * 修改订单状态
     *
     * @param rep
     * @param status
     * @param lastStatus
     */
    public void contrastAndChangeOrderStatus(OrderInfoRepository rep, OrderStatusEnum status, OrderStatusEnum lastStatus) {
        // 订单现有状态
        OrderStatusEnum nowStatus = OrderStatusEnum.parse(data.getStatus());
        Asserts.assertTrue(status == nowStatus, MessageFormat.format("工单状态为:{0} 设定状态为:{1},状态需要相同", nowStatus.getName(), status.getName()));
        data.setStatus(lastStatus.getCode());
        onUpdate();
        rep.save(this);
    }

    private void changeNodesOrderId() {
        if (CollectionUtil.isEmpty(nodes)) {
            return;
        }
        for (OrderNode node : nodes) {
            OrderNodeDO orderNodeDO = node.toData().orElseThrow(Asserts::throwOptionalException);
            orderNodeDO.setBaseInfoId(getUnique().orElseThrow(Asserts::throwOptionalException));
        }
    }
}
