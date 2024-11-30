package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import top.uhyils.usher.protocol.rpc.OrderBaseNodeFieldProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderBaseNodeFieldService;

/**
 * 工单节点属性样例表(OrderBaseNodeField)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分02秒
 */
@RpcService
public class OrderBaseNodeFieldProviderImpl extends BaseDefaultProvider<OrderBaseNodeFieldDTO> implements OrderBaseNodeFieldProvider {


    @Autowired
    private OrderBaseNodeFieldService service;


    @Override
    protected BaseDoService<OrderBaseNodeFieldDTO> getService() {
        return service;
    }

}

