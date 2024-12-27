package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ApiDTO;
import top.uhyils.usher.pojo.DTO.base.Page;
import top.uhyils.usher.pojo.DTO.request.GetByArgsAndGroupQuery;
import top.uhyils.usher.pojo.cqe.query.demo.Limit;
import top.uhyils.usher.pojo.cqe.query.demo.Order;
import top.uhyils.usher.protocol.rpc.ApiProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.ApiService;
import top.uhyils.usher.service.BaseDoService;

/**
 * api表(Api)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 19时46分47秒
 */
@RpcService
public class ApiProviderImpl extends BaseDefaultProvider<ApiDTO> implements ApiProvider {


    @Autowired
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

