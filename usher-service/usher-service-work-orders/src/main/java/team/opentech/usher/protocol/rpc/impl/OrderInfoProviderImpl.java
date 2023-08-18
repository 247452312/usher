package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.InitOrderDTO;
import team.opentech.usher.pojo.DTO.OrderInfoDTO;
import team.opentech.usher.pojo.cqe.command.CommitOrderCommand;
import team.opentech.usher.pojo.cqe.command.FrozenOrderCommand;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.command.RecallOrderCommand;
import team.opentech.usher.pojo.cqe.command.RestartOrderCommand;
import team.opentech.usher.pojo.cqe.event.AgreeRecallOrderEvent;
import team.opentech.usher.pojo.cqe.event.ApprovalOrderEvent;
import team.opentech.usher.pojo.cqe.query.GetAllOrderQuery;
import team.opentech.usher.protocol.rpc.OrderInfoProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工单基础信息样例表(OrderInfo)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分15秒
 */
@RpcService
public class OrderInfoProviderImpl extends BaseDefaultProvider<OrderInfoDTO> implements OrderInfoProvider {


    @Autowired
    private OrderInfoService service;

    @Override
    public InitOrderDTO initOrder(IdCommand request) {
        return service.initOrder(request);
    }

    @Override
    public List<OrderInfoDTO> getAllOrder(GetAllOrderQuery request) {
        return service.getAllOrder(request);
    }

    @Override
    public Boolean commitOrder(CommitOrderCommand request) {
        return service.commitOrder(request);
    }

    @Override
    public Boolean recallOrder(RecallOrderCommand request) {
        return service.recallOrder(request);
    }

    @Override
    public Boolean agreeRecallOrder(AgreeRecallOrderEvent request) {
        return service.agreeRecallOrder(request);
    }

    @Override
    public Boolean frozenOrder(FrozenOrderCommand request) {
        return service.frozenOrder(request);
    }

    @Override
    public Boolean restartOrder(RestartOrderCommand request) {
        return service.restartOrder(request);
    }

    @Override
    public Boolean approvalOrder(ApprovalOrderEvent request) {
        return service.approvalOrder(request);
    }

    @Override
    protected BaseDoService<OrderInfoDTO> getService() {
        return service;
    }
}

