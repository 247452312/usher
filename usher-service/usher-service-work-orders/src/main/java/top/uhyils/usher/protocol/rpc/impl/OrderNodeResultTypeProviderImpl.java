package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderNodeResultTypeDTO;
import top.uhyils.usher.protocol.rpc.OrderNodeResultTypeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderNodeResultTypeService;

/**
 * 工单节点处理结果样例表(OrderNodeResultType)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分31秒
 */
@RpcService
public class OrderNodeResultTypeProviderImpl extends BaseDefaultProvider<OrderNodeResultTypeDTO> implements OrderNodeResultTypeProvider {


    @Autowired
    private OrderNodeResultTypeService service;


    @Override
    protected BaseDoService<OrderNodeResultTypeDTO> getService() {
        return service;
    }

}

