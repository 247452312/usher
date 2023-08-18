package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.LogMonitorDTO;
import team.opentech.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import team.opentech.usher.pojo.DTO.response.JvmInfoLogDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.protocol.rpc.LogMonitorProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.LogMonitorService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * JVM日志表(LogMonitor)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@RpcService
public class LogMonitorProviderImpl extends BaseDefaultProvider<LogMonitorDTO> implements LogMonitorProvider {


    @Autowired
    private LogMonitorService service;

    @Override
    public JvmDataStatisticsDTO getJvmDataStatisticsResponse(DefaultCQE request) {
        return service.getJvmDataStatisticsResponse(request);
    }

    @Override
    public JvmInfoLogDTO getJvmInfoLogResponse(DefaultCQE request) {
        return service.getJvmInfoLogResponse(request);
    }

    @Override
    protected BaseDoService<LogMonitorDTO> getService() {
        return service;
    }
}

