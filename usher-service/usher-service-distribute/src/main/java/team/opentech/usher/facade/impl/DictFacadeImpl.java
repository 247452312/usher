package team.opentech.usher.facade.impl;

import team.opentech.usher.annotation.Facade;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.facade.DictFacade;
import team.opentech.usher.pojo.DTO.response.LastPlanDTO;
import team.opentech.usher.pojo.DTO.response.QuickStartDTO;
import team.opentech.usher.pojo.DTO.response.VersionInfoDTO;
import team.opentech.usher.protocol.rpc.DictProvider;
import team.opentech.usher.rpc.annotation.RpcReference;


/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月10日 08时31分
 */
@Facade
public class DictFacadeImpl implements DictFacade {

    @RpcReference
    private DictProvider provider;

    @Override
    public QuickStartDTO quickStartInfo() {
        return provider.getQuickStartResponse(UserInfoHelper.makeCQE());
    }

    @Override
    public VersionInfoDTO versionInfo() {
        return provider.getVersionInfoResponse(UserInfoHelper.makeCQE());
    }

    @Override
    public LastPlanDTO lastPlan() {
        return provider.getLastPlanResponse(UserInfoHelper.makeCQE());
    }

}
