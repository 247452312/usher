package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.OrderLogDTO;
import team.opentech.usher.protocol.rpc.OrderLogProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;

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

