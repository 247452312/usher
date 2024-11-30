package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.ServerDTO;
import top.uhyils.usher.pojo.DTO.request.TestConnByDataRequest;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.IdQuery;
import top.uhyils.usher.protocol.rpc.ServerProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.ServerService;

/**
 * 服务器表(Server)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月02日 08时42分18秒
 */
@RpcService
public class ServerProviderImpl extends BaseDefaultProvider<ServerDTO> implements ServerProvider {


    @Autowired
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

