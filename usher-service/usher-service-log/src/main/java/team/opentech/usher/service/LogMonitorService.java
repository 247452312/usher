package team.opentech.usher.service;


import team.opentech.usher.mq.pojo.mqinfo.JvmStartInfoCommand;
import team.opentech.usher.pojo.DTO.LogMonitorDTO;
import team.opentech.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import team.opentech.usher.pojo.DTO.response.JvmInfoLogDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;

/**
 * JVM日志表(LogMonitor)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public interface LogMonitorService extends BaseDoService<LogMonitorDTO> {

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

    /**
     * 接收JVM启动信息
     *
     * @param jvmStartInfo
     */
    void receiveJvmStartInfo(JvmStartInfoCommand jvmStartInfo);
}
