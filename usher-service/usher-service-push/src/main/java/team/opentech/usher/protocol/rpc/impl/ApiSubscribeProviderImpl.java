package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ApiSubscribeDTO;
import team.opentech.usher.pojo.DTO.request.SubscribeRequest;
import team.opentech.usher.protocol.rpc.ApiSubscribeProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.ApiSubscribeService;
import team.opentech.usher.service.BaseDoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * api订阅表(ApiSubscribe)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分56秒
 */
@RpcService
public class ApiSubscribeProviderImpl extends BaseDefaultProvider<ApiSubscribeDTO> implements ApiSubscribeProvider {


    @Autowired
    private ApiSubscribeService service;

    @Override
    public Boolean subscribe(SubscribeRequest request) {
        return service.subscribe(request);
    }

    @Override
    protected BaseDoService<ApiSubscribeDTO> getService() {
        return service;
    }
}

