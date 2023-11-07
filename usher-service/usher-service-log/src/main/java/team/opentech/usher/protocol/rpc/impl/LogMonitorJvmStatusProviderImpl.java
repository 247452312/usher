package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.LogMonitorJvmStatusDTO;
import team.opentech.usher.protocol.rpc.LogMonitorJvmStatusProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.LogMonitorJvmStatusService;
import javax.annotation.Resource;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@RpcService
public class LogMonitorJvmStatusProviderImpl extends BaseDefaultProvider<LogMonitorJvmStatusDTO> implements LogMonitorJvmStatusProvider {


    @Resource
    private LogMonitorJvmStatusService service;


    @Override
    protected BaseDoService<LogMonitorJvmStatusDTO> getService() {
        return service;
    }

}

