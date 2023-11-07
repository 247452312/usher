package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderNodeFieldValueDTO;
import team.opentech.usher.protocol.rpc.OrderNodeFieldValueProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderNodeFieldValueService;
import javax.annotation.Resource;

/**
 * 订单节点属性真实值表(OrderNodeFieldValue)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分28秒
 */
@RpcService
public class OrderNodeFieldValueProviderImpl extends BaseDefaultProvider<OrderNodeFieldValueDTO> implements OrderNodeFieldValueProvider {


    @Resource
    private OrderNodeFieldValueService service;


    @Override
    protected BaseDoService<OrderNodeFieldValueDTO> getService() {
        return service;
    }

}

