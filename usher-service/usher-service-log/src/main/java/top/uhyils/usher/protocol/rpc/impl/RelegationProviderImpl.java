package top.uhyils.usher.protocol.rpc.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.pojo.DTO.RelegationDTO;
import top.uhyils.usher.pojo.cqe.command.RelegationCoverCommand;
import top.uhyils.usher.pojo.cqe.command.RelegationDemotionCommand;
import top.uhyils.usher.protocol.rpc.RelegationProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.RelegationService;

/**
 * 接口降级策略(Relegation)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分25秒
 */
@RpcService
public class RelegationProviderImpl extends BaseDefaultProvider<RelegationDTO> implements RelegationProvider {


    @Autowired
    private RelegationService service;

    @Override
    public Boolean demotion(RelegationDemotionCommand cqe) {
        return service.demotion(cqe.getServiceName(), cqe.getMethodName());
    }

    @Override
    public Boolean recover(RelegationCoverCommand cqe) {
        return service.recover(cqe.getServiceName(), cqe.getMethodName());
    }

    @Override
    protected BaseDoService<RelegationDTO> getService() {
        return service;
    }
}

