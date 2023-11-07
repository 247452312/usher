package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ApiDTO;
import team.opentech.usher.pojo.DTO.base.Page;
import team.opentech.usher.pojo.DTO.request.GetByArgsAndGroupQuery;
import team.opentech.usher.pojo.cqe.query.demo.Limit;
import team.opentech.usher.pojo.cqe.query.demo.Order;
import team.opentech.usher.protocol.rpc.ApiProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.ApiService;
import team.opentech.usher.service.BaseDoService;
import javax.annotation.Resource;

/**
 * api表(Api)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分47秒
 */
@RpcService
public class ApiProviderImpl extends BaseDefaultProvider<ApiDTO> implements ApiProvider {


    @Resource
    private ApiService service;

    @Override
    public Page<ApiDTO> getByArgsAndGroup(GetByArgsAndGroupQuery request) {
        Long groupId = request.getGroupId();
        Order order = request.getOrder();
        Limit limit = request.getLimit();
        return service.getByArgsAndGroup(groupId, order, limit);
    }

    @Override
    protected BaseDoService<ApiDTO> getService() {
        return service;
    }
}

