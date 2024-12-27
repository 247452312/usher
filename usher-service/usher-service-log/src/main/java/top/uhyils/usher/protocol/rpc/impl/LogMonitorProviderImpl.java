package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.LogMonitorDTO;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.LogMonitorProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.LogMonitorService;

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

