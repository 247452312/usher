package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ApiGroupDTO;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.ApiGroupProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.ApiGroupService;
import team.opentech.usher.service.BaseDoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

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

