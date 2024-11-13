package team.opentech.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import team.opentech.usher.pojo.DTO.AiSpaceDTO;
import team.opentech.usher.protocol.rpc.AiSpaceProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.AiSpaceService;
import team.opentech.usher.service.BaseDoService;

/**
 * 独立空间(AiSpace)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2024年11月13日 09时38分
 */
@RpcService
public class AiSpaceProviderImpl extends BaseDefaultProvider<AiSpaceDTO> implements AiSpaceProvider {


    @Autowired
    private AiSpaceService service;


    @Override
    protected BaseDoService<AiSpaceDTO> getService() {
        return service;
    }

}

