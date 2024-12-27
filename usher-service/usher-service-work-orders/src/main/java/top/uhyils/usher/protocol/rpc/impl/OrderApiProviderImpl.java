package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderApiDTO;
import top.uhyils.usher.protocol.rpc.OrderApiProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderApiService;

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

