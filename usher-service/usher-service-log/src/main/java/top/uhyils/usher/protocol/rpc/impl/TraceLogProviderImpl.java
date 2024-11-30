package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.TraceLogDTO;
import top.uhyils.usher.protocol.rpc.TraceLogProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.TraceLogService;

/**
 * (TraceLog)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分55秒
 */
@RpcService
public class TraceLogProviderImpl extends BaseDefaultProvider<TraceLogDTO> implements TraceLogProvider {


    @Autowired
    private TraceLogService service;


    @Override
    protected BaseDoService<TraceLogDTO> getService() {
        return service;
    }

}

