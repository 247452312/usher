package team.opentech.usher.service.impl;

import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.assembler.OrderInfoAssembler;
import team.opentech.usher.assembler.OrderNodeAssembler;
import team.opentech.usher.enums.OrderStatusEnum;
import team.opentech.usher.enums.PushTypeEnum;
import team.opentech.usher.facade.PushFacade;
import team.opentech.usher.pojo.DO.OrderNodeDO;
import team.opentech.usher.pojo.DTO.DealOrderNodeDTO;
import team.opentech.usher.pojo.DTO.OrderNodeDTO;
import team.opentech.usher.pojo.cqe.command.DealOrderNodeCommand;
import team.opentech.usher.pojo.cqe.command.FailOrderNodeCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.command.IncapacityFailOrderNodeCommand;
import team.opentech.usher.pojo.entity.OrderApply;
import team.opentech.usher.pojo.entity.OrderNode;
import team.opentech.usher.pojo.entity.OrderNodeList;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.repository.OrderApplyRepository;
import team.opentech.usher.repository.OrderInfoRepository;
import team.opentech.usher.repository.OrderNodeFieldRepository;
import team.opentech.usher.repository.OrderNodeFieldValueRepository;
import team.opentech.usher.repository.OrderNodeRepository;
import team.opentech.usher.repository.OrderNodeResultTypeRepository;
import team.opentech.usher.repository.OrderNodeRouteRepository;
import team.opentech.usher.service.OrderNodeService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单节点样例表(OrderNode)表 内部服务实现类
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分21秒
 */
@Service
@ReadWriteMark(tables = {"sys_order_node"})
public class OrderNodeServiceImpl extends AbstractDoService<OrderNodeDO, OrderNode, OrderNodeDTO, OrderNodeRepository, OrderNodeAssembler> implements OrderNodeService {

    @Autowired
    private OrderNodeFieldRepository fieldRepository;

    @Autowired
    private OrderNodeRouteRepository routeRepository;

    @Autowired
    private OrderNodeResultTypeRepository resultTypeRepository;

    @Autowired
    private OrderNodeFieldValueRepository fieldValueRepository;

    @Autowired
    private PushFacade pushFacade;

    @Autowired
    private OrderInfoRepository infoRepository;

    @Autowired
    private OrderApplyRepository applyRepository;

    @Autowired
    private OrderInfoAssembler orderInfoAssembler;

    public OrderNodeServiceImpl(OrderNodeAssembler assembler, OrderNodeRepository repository) {
        super(assembler, repository);
    }

    @Override
    public Boolean deleteByIds(IdsCommand request) {
        List<Identifier> nodeIds = request.getIds().stream().map(Identifier::new).collect(Collectors.toList());
        List<OrderNode> orderNodes = rep.find(nodeIds);
        OrderNodeList orderNodeList = new OrderNodeList(orderNodes);
        /*删除属性*/
        orderNodeList.delFields(fieldRepository);
        /*删除路由*/
        orderNodeList.delRoutes(routeRepository);
        /*删除结果类型*/
        orderNodeList.delResultType(resultTypeRepository);
        /*删除本体*/
        orderNodeList.delSelf(rep);
        return true;
    }

    @Override
    public Boolean failOrderNode(FailOrderNodeCommand request) {
        rep.makeOrderFault(request.getOrderNodeId(), request.getMsg());
        return true;
    }

    @Override
    public DealOrderNodeDTO dealOrderNode(DealOrderNodeCommand request) {
        /*前提:判断节点值是否允许*/
        OrderNode orderNode = new OrderNode(request.getNodeId());
        orderNode.completion(rep);
        // 检验自身是否允许被修改
        orderNode.assertAllow();
        // 检验传入的值是否正确
        orderNode.fillField(fieldRepository);

        /*1.结束当前工单节点(节点状态),处理结果类型->处理成功,处理结果id选择,处理人建议*/
        orderNode.successAndClose(rep, request.getResultId(), request.getSuggest());

        orderNode.assertAndSaveFieldValues(fieldValueRepository, request.getOrderNodeFieldValueMap());

        /*2.将下一节点置为等待开始(并通知自动处理模块检测是否为自动处理模块)*/
        OrderNode next = orderNode.nextOrder(rep);
        next.start(rep);
        next.checkAuto(pushFacade, orderNode);
        return DealOrderNodeDTO.buildSuccess();
    }

    @Override
    public Boolean incapacityFailOrderNode(IncapacityFailOrderNodeCommand request) {
        OrderNode orderNode = new OrderNode(request.getOrderNodeId());
        orderNode.completion(rep);
        /*1.将节点状态置为转交中*/
        orderNode.transfer(rep);
        /*2.插入申请表*/
        OrderApply apply = assem.toApply(orderNode, orderNode.findBaseInfo(infoRepository));
        apply.saveSelf(applyRepository);
        /*3.通知审批人*/
        apply.noticeTargetUser(infoRepository, orderInfoAssembler, OrderStatusEnum.CIRCULATION, pushFacade, PushTypeEnum.EMAIL);
        return true;
    }
}
