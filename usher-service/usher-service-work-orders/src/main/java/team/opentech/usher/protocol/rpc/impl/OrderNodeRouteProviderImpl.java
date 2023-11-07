package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderNodeRouteDTO;
import team.opentech.usher.protocol.rpc.OrderNodeRouteProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderNodeRouteService;
import javax.annotation.Resource;

/**
 * 节点间关联路由样例表(OrderNodeRoute)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分35秒
 */
@RpcService
public class OrderNodeRouteProviderImpl extends BaseDefaultProvider<OrderNodeRouteDTO> implements OrderNodeRouteProvider {


    @Resource
    private OrderNodeRouteService service;


    @Override
    protected BaseDoService<OrderNodeRouteDTO> getService() {
        return service;
    }

}

