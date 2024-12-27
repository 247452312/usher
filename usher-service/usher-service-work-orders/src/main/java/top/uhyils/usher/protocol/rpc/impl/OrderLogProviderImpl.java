package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.OrderLogDTO;
import top.uhyils.usher.protocol.rpc.OrderLogProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.OrderLogService;

/**
 * 日志表(OrderLog)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月31日 19时59分18秒
 */
@RpcService
public class OrderLogProviderImpl extends BaseDefaultProvider<OrderLogDTO> implements OrderLogProvider {


    @Autowired
    private OrderLogService service;


    @Override
    protected BaseDoService<OrderLogDTO> getService() {
        return service;
    }

}

