package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderApiDTO;
import team.opentech.usher.protocol.rpc.OrderApiProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderApiService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 节点api表(OrderApi)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分47秒
 */
@RpcService
public class OrderApiProviderImpl extends BaseDefaultProvider<OrderApiDTO> implements OrderApiProvider {


    @Autowired
    private OrderApiService service;


    @Override
    protected BaseDoService<OrderApiDTO> getService() {
        return service;
    }

}

