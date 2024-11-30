package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ApiSubscribeDTO;
import top.uhyils.usher.pojo.DTO.request.SubscribeRequest;
import top.uhyils.usher.protocol.rpc.ApiSubscribeProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.ApiSubscribeService;
import top.uhyils.usher.service.BaseDoService;

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

