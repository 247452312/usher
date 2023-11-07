package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderBaseNodeFieldDTO;
import team.opentech.usher.protocol.rpc.OrderBaseNodeFieldProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderBaseNodeFieldService;
import javax.annotation.Resource;

/**
 * 工单节点属性样例表(OrderBaseNodeField)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分02秒
 */
@RpcService
public class OrderBaseNodeFieldProviderImpl extends BaseDefaultProvider<OrderBaseNodeFieldDTO> implements OrderBaseNodeFieldProvider {


    @Resource
    private OrderBaseNodeFieldService service;


    @Override
    protected BaseDoService<OrderBaseNodeFieldDTO> getService() {
        return service;
    }

}

