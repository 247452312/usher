package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderApplyDTO;
import top.uhyils.usher.protocol.rpc.OrderApplyProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderApplyService;

/**
 * (OrderApply)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时58分51秒
 */
@RpcService
public class OrderApplyProviderImpl extends BaseDefaultProvider<OrderApplyDTO> implements OrderApplyProvider {


    @Autowired
    private OrderApplyService service;


    @Override
    protected BaseDoService<OrderApplyDTO> getService() {
        return service;
    }

}

