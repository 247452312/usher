package team.opentech.usher.service;


import team.opentech.usher.mq.pojo.mqinfo.JvmStatusInfoCommand;
import team.opentech.usher.pojo.DTO.LogMonitorJvmStatusDTO;

/**
 * JVM状态子表(LogMonitorJvmStatus)表 内部服务接口
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月29日 16时58分53秒
 */
public interface LogMonitorJvmStatusService extends BaseDoService<LogMonitorJvmStatusDTO> {

    /**
     * 接收到服务状态信息
     *
     * @param jvmStatusInfo
     */
    void receiveJvmStatusInfo(JvmStatusInfoCommand jvmStatusInfo);
}
