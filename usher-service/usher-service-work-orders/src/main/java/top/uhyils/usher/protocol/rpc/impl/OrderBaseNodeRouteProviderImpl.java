package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeRouteDTO;
import top.uhyils.usher.protocol.rpc.OrderBaseNodeRouteProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderBaseNodeRouteService;

/**
 * 节点间关联路由样例表(OrderBaseNodeRoute)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分09秒
 */
@RpcService
public class OrderBaseNodeRouteProviderImpl extends BaseDefaultProvider<OrderBaseNodeRouteDTO> implements OrderBaseNodeRouteProvider {


    @Autowired
    private OrderBaseNodeRouteService service;


    @Override
    protected BaseDoService<OrderBaseNodeRouteDTO> getService() {
        return service;
    }

}

