package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.InitOrderDTO;
import top.uhyils.usher.pojo.DTO.OrderInfoDTO;
import top.uhyils.usher.pojo.cqe.command.CommitOrderCommand;
import top.uhyils.usher.pojo.cqe.command.FrozenOrderCommand;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.command.RecallOrderCommand;
import top.uhyils.usher.pojo.cqe.command.RestartOrderCommand;
import top.uhyils.usher.pojo.cqe.event.AgreeRecallOrderEvent;
import top.uhyils.usher.pojo.cqe.event.ApprovalOrderEvent;
import top.uhyils.usher.pojo.cqe.query.GetAllOrderQuery;
import top.uhyils.usher.protocol.rpc.OrderInfoProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderInfoService;

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

