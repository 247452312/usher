package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeDTO;
import top.uhyils.usher.protocol.rpc.OrderBaseNodeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderBaseNodeService;

/**
 * 工单节点样例表(OrderBaseNode)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分58秒
 */
@RpcService
public class OrderBaseNodeProviderImpl extends BaseDefaultProvider<OrderBaseNodeDTO> implements OrderBaseNodeProvider {


    @Autowired
    private OrderBaseNodeService service;


    @Override
    protected BaseDoService<OrderBaseNodeDTO> getService() {
        return service;
    }

}

