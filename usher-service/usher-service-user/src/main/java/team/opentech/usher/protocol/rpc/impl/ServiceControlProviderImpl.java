package team.opentech.usher.protocol.rpc.impl;

import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.DTO.request.DelMethodDisableCommand;
import team.opentech.usher.pojo.DTO.request.MethodDisableQuery;
import team.opentech.usher.pojo.cqe.DefaultCQE;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import team.opentech.usher.protocol.rpc.ServiceControlProvider;
import team.opentech.usher.rpc.annotation.RpcService;
import team.opentech.usher.service.ServiceControlService;
import team.opentech.usher.util.Asserts;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接口禁用服务
 *
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年05月27日 16时28分
 */
@RpcService
public class ServiceControlProviderImpl implements ServiceControlProvider {


    @Autowired
    private ServiceControlService service;

    @Override
    public Boolean getMethodDisable(MethodDisableQuery request) {
        InterfaceName className = new InterfaceName(request.getClassName());
        MethodName methodName = new MethodName(request.getMethodName());
        ReadWriteTypeEnum methodType = ReadWriteTypeEnum.parse(request.getMethodType()).orElseThrow(() -> Asserts.makeException("方法禁用时,未查询到方法类型"));
        return service.getMethodDisable(className, methodName, methodType);
    }

    @Override
    public List<MethodDisableDTO> getAllMethodDisable(DefaultCQE request) {
        return service.getAllMethodDisable();
    }

    @Override
    public Boolean addOrEditMethodDisable(AddCommand<MethodDisableDTO> request) {
        MethodDisableDTO dto = request.getDto();
        return service.addOrEditMethodDisable(dto);
    }

    @Override
    public Boolean delMethodDisable(DelMethodDisableCommand request) {
        InterfaceName interfaceName = new InterfaceName(request.getClassName());
        MethodName methodName = new MethodName(request.getMethodName());
        return service.delMethodDisable(interfaceName, methodName);
    }
}
