package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ApiGroupDTO;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.ApiGroupProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.ApiGroupService;
import top.uhyils.usher.service.BaseDoService;

/**
 * api组表(ApiGroup)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分52秒
 */
@RpcService
public class ApiGroupProviderImpl extends BaseDefaultProvider<ApiGroupDTO> implements ApiGroupProvider {


    @Autowired
    private ApiGroupService service;

    @Override
    public String test(IdQuery request) {
        return service.test(request);
    }

    @Override
    public List<ApiGroupDTO> getCanBeSubscribed(DefaultCQE request) {
        return service.getCanBeSubscribed(request);
    }

    @Override
    protected BaseDoService<ApiGroupDTO> getService() {
        return service;
    }
}

