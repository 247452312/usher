package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderNodeFieldDTO;
import top.uhyils.usher.protocol.rpc.OrderNodeFieldProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderNodeFieldService;

/**
 * 工单节点属性样例表(OrderNodeField)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分25秒
 */
@RpcService
public class OrderNodeFieldProviderImpl extends BaseDefaultProvider<OrderNodeFieldDTO> implements OrderNodeFieldProvider {


    @Autowired
    private OrderNodeFieldService service;


    @Override
    protected BaseDoService<OrderNodeFieldDTO> getService() {
        return service;
    }

}

