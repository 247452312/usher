package top.uhyils.usher.facade.impl;

import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.context.UserInfoHelper;
import top.uhyils.usher.facade.DictFacade;
import top.uhyils.usher.pojo.DTO.response.LastPlanDTO;
import top.uhyils.usher.pojo.DTO.response.QuickStartDTO;
import top.uhyils.usher.pojo.DTO.response.VersionInfoDTO;
import top.uhyils.usher.protocol.rpc.DictProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;


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
