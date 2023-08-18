package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.TraceLogDTO;
import team.opentech.usher.protocol.rpc.TraceLogProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.TraceLogService;
import org.springframework.beans.factory.annotation.Autowired;

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

