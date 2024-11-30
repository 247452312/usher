package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.context.UserInfoHelper;
import top.uhyils.usher.facade.JvmFacade;
import top.uhyils.usher.pojo.DTO.response.JvmDataStatisticsDTO;
import top.uhyils.usher.pojo.DTO.response.JvmInfoLogDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.protocol.rpc.LogMonitorProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;


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
