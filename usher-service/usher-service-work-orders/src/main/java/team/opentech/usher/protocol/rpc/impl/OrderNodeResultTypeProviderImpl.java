package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderNodeResultTypeDTO;
import team.opentech.usher.protocol.rpc.OrderNodeResultTypeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderNodeResultTypeService;
import javax.annotation.Resource;

/**
 * 工单节点处理结果样例表(OrderNodeResultType)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分31秒
 */
@RpcService
public class OrderNodeResultTypeProviderImpl extends BaseDefaultProvider<OrderNodeResultTypeDTO> implements OrderNodeResultTypeProvider {


    @Resource
    private OrderNodeResultTypeService service;


    @Override
    protected BaseDoService<OrderNodeResultTypeDTO> getService() {
        return service;
    }

}

