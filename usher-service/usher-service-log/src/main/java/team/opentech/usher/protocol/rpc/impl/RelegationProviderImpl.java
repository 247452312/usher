package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.cqe.command.RelegationCoverCommand;
import team.opentech.usher.pojo.cqe.command.RelegationDemotionCommand;
import team.opentech.usher.protocol.rpc.RelegationProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.RelegationService;
import javax.annotation.Resource;

/**
 * 接口降级策略(Relegation)表 RPC对外访问实现
 *
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月27日 09时33分25秒
 */
@RpcService
public class RelegationProviderImpl extends BaseDefaultProvider<RelegationDTO> implements RelegationProvider {


    @Resource
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

