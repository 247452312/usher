package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.annotation.Public;
import top.uhyils.usher.pojo.DTO.PowerDTO;
import top.uhyils.usher.pojo.DTO.request.GetMethodNameByInterfaceNameQuery;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.IdCommand;
import top.uhyils.usher.pojo.cqe.query.CheckUserHavePowerQuery;
import top.uhyils.usher.pojo.entity.type.InterfaceName;
import top.uhyils.usher.pojo.entity.type.MethodName;
import top.uhyils.usher.protocol.rpc.PowerProvider;
import top.uhyils.usher.protocol.rpc.base.BaseDefaultProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.BaseDoService;
import top.uhyils.usher.service.PowerService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@RpcService
public class PowerProviderImpl extends BaseDefaultProvider<PowerDTO> implements PowerProvider {


    @Autowired
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
        return service.checkUserHavePower(interfaceName, methodName, request.getUserId());
    }

    @Override
    public Boolean deletePower(IdCommand request) {
        return service.deletePower(request.getId());

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
