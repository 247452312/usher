package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.ServerDTO;
import team.opentech.usher.pojo.DTO.request.TestConnByDataRequest;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.IdQuery;
import team.opentech.usher.protocol.rpc.ServerProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.ServerService;
import java.util.List;
import javax.annotation.Resource;

/**
 * 服务器表(Server)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分18秒
 */
@RpcService
public class ServerProviderImpl extends BaseDefaultProvider<ServerDTO> implements ServerProvider {


    @Resource
    private ServerService service;

    @Override
    public Boolean testConnByData(TestConnByDataRequest request) {
        return service.testConnByData(request);
    }

    @Override
    public Boolean testConnById(IdCommand request) {
        return service.testConnById(request);
    }

    @Override
    public List<ServerDTO> getServersIdAndName(DefaultCQE request) {
        return service.getServersIdAndName(request);
    }

    @Override
    public String getNameById(IdQuery request) {
        return service.getNameById(request);
    }

    @Override
    protected BaseDoService<ServerDTO> getService() {
        return service;
    }
}

