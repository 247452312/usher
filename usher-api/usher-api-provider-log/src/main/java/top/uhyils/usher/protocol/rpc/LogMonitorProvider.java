package top.uhyils.usher.protocol.rpc;

import top.uhyils.usher.pojo.DTO.LogMonitorDTO;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.base.DTOProvider;

/**
 * JVM日志表(LogMonitor)表 Rpc对外访问层
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public interface LogMonitorProvider extends DTOProvider<LogMonitorDTO> {

    /**
     * 获取JVM数据统计信息
     *
     * @param request 默认请求
     *
     * @return JVM数据统计信息
     */
    JvmDataStatisticsDTO getJvmDataStatisticsResponse(DefaultCQE request);


    /**
     * 获取JVM历史信息 -> 存活的
     *
     * @param request 请求
     *
     * @return JVM历史信息 -> 存活的
     */
    JvmInfoLogDTO getJvmInfoLogResponse(DefaultCQE request);

}

