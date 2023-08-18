package team.opentech.usher.facade.impl;

import team.opentech.usher.annotation.Facade;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.facade.JvmFacade;
import team.opentech.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import team.opentech.usher.pojo.DTO.response.JvmInfoLogDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.protocol.rpc.LogMonitorProvider;
import team.opentech.usher.rpc.annotation.RpcReference;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月10日 08时31分
 */
@Facade
public class JvmFacadeImpl implements JvmFacade {

    @RpcReference
    private LogMonitorProvider jvmProvider;

    @Override
    public JvmDataStatisticsDTO jvmStatisticDate() {
        DefaultCQE request = UserInfoHelper.makeCQE();
        return jvmProvider.getJvmDataStatisticsResponse(request);
    }

    @Override
    public JvmInfoLogDTO jvmInfoLog() {
        DefaultCQE request = UserInfoHelper.makeCQE();
        return jvmProvider.getJvmInfoLogResponse(request);
    }
}
