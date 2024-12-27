package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderBaseNodeResultTypeDTO;
import top.uhyils.usher.protocol.rpc.OrderBaseNodeResultTypeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderBaseNodeResultTypeService;

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

