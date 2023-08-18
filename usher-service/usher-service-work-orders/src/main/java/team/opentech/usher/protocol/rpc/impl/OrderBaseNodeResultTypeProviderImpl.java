package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderBaseNodeResultTypeDTO;
import team.opentech.usher.protocol.rpc.OrderBaseNodeResultTypeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderBaseNodeResultTypeService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工单节点处理结果样例表(OrderBaseNodeResultType)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分05秒
 */
@RpcService
public class OrderBaseNodeResultTypeProviderImpl extends BaseDefaultProvider<OrderBaseNodeResultTypeDTO> implements OrderBaseNodeResultTypeProvider {


    @Autowired
    private OrderBaseNodeResultTypeService service;


    @Override
    protected BaseDoService<OrderBaseNodeResultTypeDTO> getService() {
        return service;
    }

}

