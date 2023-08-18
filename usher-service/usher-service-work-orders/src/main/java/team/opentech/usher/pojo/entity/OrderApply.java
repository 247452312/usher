package team.opentech.usher.pojo.entity;

import team.opentech.usher.annotation.Default;
import team.opentech.usher.assembler.OrderInfoAssembler;
import team.opentech.usher.enums.OrderNodeStatusEnum;
import team.opentech.usher.enums.OrderStatusEnum;
import team.opentech.usher.enums.PushTypeEnum;
import team.opentech.usher.facade.PushFacade;
import team.opentech.usher.pojo.DO.OrderApplyDO;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DO.base.BaseIdDO;
import team.opentech.usher.pojo.entity.base.AbstractDoEntity;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.OrderApplyRepository;
import team.opentech.usher.repository.OrderInfoRepository;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.util.Asserts;
import java.util.List;

/**
 * (OrderApply)表 数据库实体类
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2021年08月31日 19时58分49秒
 */
public class OrderApply extends AbstractDoEntity<OrderApplyDO> {

    /**
     * 这个申请的节点
     */
    private OrderNode orderNode;

    @Default
    public OrderApply(OrderApplyDO data) {
        super(data);
    }

    public OrderApply(Long id) {
        super(id, new OrderApplyDO());
    }

    public void completionThisNode(OrderNodeRepository nodeRepository, OrderNodeFieldRepository fieldRepository, OrderNodeRouteRepository routeRepository, OrderNodeResultTypeRepository resultTypeRepository) {
        if (this.orderNode != null) {
            return;
        }

        this.orderNode = nodeRepository.find(new Identifier(toData().map(OrderApplyDO::getOrderNodeId).orElseThrow(Asserts::throwOptionalException)));
        List<OrderNodeField> fields = fieldRepository.findByNodeId(orderNode.toData().map(BaseIdDO::getId).orElseThrow(Asserts::throwOptionalException));
        List<OrderNodeResultType> resultTypes = resultTypeRepository.findByNodeId(orderNode.toData().map(BaseIdDO::getId).orElseThrow(Asserts::throwOptionalException));
        List<OrderNodeRoute> route = routeRepository.findByNodeId(orderNode.toData().map(BaseIdDO::getId).orElseThrow(Asserts::throwOptionalException));
        this.orderNode.forceFillInfo(fields, resultTypes, route);

    }

    public void changeThisNodeStatus(OrderNodeRepository orderNodeRepository, OrderNodeStatusEnum status) {
        Asserts.assertTrue(orderNode != null, "节点没有初始化");
        orderNode.changeStatus(orderNodeRepository, status);

    }

    public OrderNode copyToLastNode() {
        OrderNode copy = orderNode.copy();
        OrderNodeDO orderNodeDO = copy.toData().orElseThrow(Asserts::throwOptionalException);
        orderNodeDO.setStatus(OrderStatusEnum.STOP.getCode());
        orderNodeDO.setName(orderNodeDO.getName() + "(转交)");
        orderNodeDO.setRunDealUserId(data.getTargetUserId());
        orderNodeDO.preInsert();
        return copy;

    }

    public OrderNode node() {
        return orderNode;
    }

    public void noticeTargetUser(OrderInfoRepository rep, OrderInfoAssembler assem, PushFacade pushFacade, PushTypeEnum email) {
        noticeTargetUser(rep, assem, OrderStatusEnum.CIRCULATION, pushFacade, email);
    }

    public void noticeTargetUser(OrderInfoRepository rep, OrderInfoAssembler assem, OrderStatusEnum status, PushFacade pushFacade, PushTypeEnum email) {
        OrderInfo orderInfo = rep.find(new Identifier(data.getOrderId()));
        pushFacade.pushMsg(assem.toDTO(orderInfo), data.getTargetUserId(), status, email);
    }

    public void saveSelf(OrderApplyRepository rep) {
        rep.save(this);
    }
}
