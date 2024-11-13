package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiSubspaceDTO;
import team.opentech.usher.protocol.rpc.AiSubspaceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiSubspaceService;
import team.opentech.usher.service.BaseDoService;

/**
 * 子空间(AiSubspace)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@RpcService
public class AiSubspaceProviderImpl extends BaseDefaultProvider<AiSubspaceDTO> implements AiSubspaceProvider {


    @Autowired
    private AiSubspaceService service;


    @Override
    protected BaseDoService<AiSubspaceDTO> getService() {
        return service;
    }

}

