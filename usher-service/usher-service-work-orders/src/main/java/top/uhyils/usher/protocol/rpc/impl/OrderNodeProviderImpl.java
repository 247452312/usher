package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.DealOrderNodeDTO;
import top.uhyils.usher.pojo.DTO.OrderNodeDTO;
import top.uhyils.usher.pojo.cqe.command.DealOrderNodeCommand;
import top.uhyils.usher.pojo.cqe.command.FailOrderNodeCommand;
import top.uhyils.usher.pojo.cqe.command.IdsCommand;
import top.uhyils.usher.pojo.cqe.command.IncapacityFailOrderNodeCommand;
import top.uhyils.usher.protocol.rpc.OrderNodeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderNodeService;

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

