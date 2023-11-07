package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderApplyDTO;
import team.opentech.usher.protocol.rpc.OrderApplyProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderApplyService;
import javax.annotation.Resource;

/**
 * (OrderApply)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分51秒
 */
@RpcService
public class OrderApplyProviderImpl extends BaseDefaultProvider<OrderApplyDTO> implements OrderApplyProvider {


    @Resource
    private OrderApplyService service;


    @Override
    protected BaseDoService<OrderApplyDTO> getService() {
        return service;
    }

}

