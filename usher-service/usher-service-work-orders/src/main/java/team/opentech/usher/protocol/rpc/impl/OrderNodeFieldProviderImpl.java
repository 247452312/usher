package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderNodeFieldDTO;
import team.opentech.usher.protocol.rpc.OrderNodeFieldProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderNodeFieldService;
import javax.annotation.Resource;

/**
 * 工单节点属性样例表(OrderNodeField)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分25秒
 */
@RpcService
public class OrderNodeFieldProviderImpl extends BaseDefaultProvider<OrderNodeFieldDTO> implements OrderNodeFieldProvider {


    @Resource
    private OrderNodeFieldService service;


    @Override
    protected BaseDoService<OrderNodeFieldDTO> getService() {
        return service;
    }

}

