package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiSpaceUserLinkDTO;
import team.opentech.usher.protocol.rpc.AiSpaceUserLinkProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiSpaceUserLinkService;
import team.opentech.usher.service.BaseDoService;

/**
 * 独立空间-用户关联表(AiSpaceUserLink)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@RpcService
public class AiSpaceUserLinkProviderImpl extends BaseDefaultProvider<AiSpaceUserLinkDTO> implements AiSpaceUserLinkProvider {


    @Autowired
    private AiSpaceUserLinkService service;


    @Override
    protected BaseDoService<AiSpaceUserLinkDTO> getService() {
        return service;
    }

}

