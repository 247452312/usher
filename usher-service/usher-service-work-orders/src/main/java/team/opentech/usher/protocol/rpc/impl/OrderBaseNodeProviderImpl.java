package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderBaseNodeDTO;
import team.opentech.usher.protocol.rpc.OrderBaseNodeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderBaseNodeService;
import javax.annotation.Resource;

/**
 * 工单节点样例表(OrderBaseNode)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分58秒
 */
@RpcService
public class OrderBaseNodeProviderImpl extends BaseDefaultProvider<OrderBaseNodeDTO> implements OrderBaseNodeProvider {


    @Resource
    private OrderBaseNodeService service;


    @Override
    protected BaseDoService<OrderBaseNodeDTO> getService() {
        return service;
    }

}

