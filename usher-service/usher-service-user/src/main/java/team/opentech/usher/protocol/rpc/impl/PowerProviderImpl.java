package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.annotation.Public;
import team.opentech.usher.pojo.DTO.PowerDTO;
import team.opentech.usher.pojo.DTO.request.GetMethodNameByInterfaceNameQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.IdCommand;
import team.opentech.usher.pojo.cqe.query.CheckUserHavePowerQuery;
import team.opentech.usher.pojo.entity.type.Identifier;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import team.opentech.usher.protocol.rpc.PowerProvider;
import team.opentech.usher.protocol.rpc.base.BaseDefaultProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.BaseDoService;
import team.opentech.usher.service.PowerService;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@RpcService
public class PowerProviderImpl extends BaseDefaultProvider<PowerDTO> implements PowerProvider {


    @Resource
    private PowerService service;

    @Override
    public List<PowerDTO> getPowers(DefaultCQE request) {
        return service.getPowers();

    }

    @Override
    @Public
    public Boolean checkUserHavePower(CheckUserHavePowerQuery request) {
        InterfaceName interfaceName = new InterfaceName(request.getInterfaceName());
        MethodName methodName = new MethodName(request.getMethodName());
        Identifier userId = new Identifier(request.getUserId());
        return service.checkUserHavePower(interfaceName, methodName, userId);
    }

    @Override
    public Boolean deletePower(IdCommand request) {
        Identifier powerId = new Identifier(request.getId());
        return service.deletePower(powerId);

    }

    @Override
    public List<String> getInterfaces(DefaultCQE request) {
        return service.getInterfaces();

    }

    @Override
    public List<String> getMethodNameByInterfaceName(GetMethodNameByInterfaceNameQuery request) {
        InterfaceName interfaceName = new InterfaceName(request.getInterfaceName());
        return service.getMethodNameByInterfaceName(interfaceName);

    }

    @Override
    public Integer initPowerInProStart(DefaultCQE request) {
        return service.initPowerInProStart();

    }

    @Override
    protected BaseDoService<PowerDTO> getService() {
        return service;
    }
}
