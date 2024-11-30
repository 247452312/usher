package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.LogMonitorJvmStatusDTO;
import top.uhyils.usher.protocol.rpc.LogMonitorJvmStatusProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.LogMonitorJvmStatusService;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
@RpcService
public class LogMonitorJvmStatusProviderImpl extends BaseDefaultProvider<LogMonitorJvmStatusDTO> implements LogMonitorJvmStatusProvider {


    @Autowired
    private LogMonitorJvmStatusService service;


    @Override
    protected BaseDoService<LogMonitorJvmStatusDTO> getService() {
        return service;
    }

}

