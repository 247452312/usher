package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderNodeRouteDTO;
import top.uhyils.usher.protocol.rpc.OrderNodeRouteProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderNodeRouteService;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分35秒
 */
@RpcService
public class OrderNodeRouteProviderImpl extends BaseDefaultProvider<OrderNodeRouteDTO> implements OrderNodeRouteProvider {


    @Autowired
    private OrderNodeRouteService service;


    @Override
    protected BaseDoService<OrderNodeRouteDTO> getService() {
        return service;
    }

}

