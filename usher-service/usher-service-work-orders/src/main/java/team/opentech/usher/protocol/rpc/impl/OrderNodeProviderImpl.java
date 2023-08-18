package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.DealOrderNodeDTO;
import team.opentech.usher.pojo.DTO.OrderNodeDTO;
import team.opentech.usher.pojo.cqe.command.DealOrderNodeCommand;
import team.opentech.usher.pojo.cqe.command.FailOrderNodeCommand;
import team.opentech.usher.pojo.cqe.command.IdsCommand;
import team.opentech.usher.pojo.cqe.command.IncapacityFailOrderNodeCommand;
import team.opentech.usher.protocol.rpc.OrderNodeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderNodeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工单节点样例表(OrderNode)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分22秒
 */
@RpcService
public class OrderNodeProviderImpl extends BaseDefaultProvider<OrderNodeDTO> implements OrderNodeProvider {


    @Autowired
    private OrderNodeService service;

    @Override
    public Boolean deleteByIds(IdsCommand request) {
        return service.deleteByIds(request);
    }

    @Override
    public Boolean failOrderNode(FailOrderNodeCommand request) {
        return service.failOrderNode(request);
    }

    @Override
    public DealOrderNodeDTO dealOrderNode(DealOrderNodeCommand request) throws Exception {
        return service.dealOrderNode(request);
    }

    @Override
    public Boolean incapacityFailOrderNode(IncapacityFailOrderNodeCommand request) throws Exception {
        return service.incapacityFailOrderNode(request);
    }

    @Override
    protected BaseDoService<OrderNodeDTO> getService() {
        return service;
    }
}

