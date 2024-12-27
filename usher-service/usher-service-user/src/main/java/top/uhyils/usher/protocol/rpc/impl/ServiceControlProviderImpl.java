package top.uhyils.usher.protocol.rpc.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.pojo.DTO.MethodDisableDTO;
import top.uhyils.usher.pojo.DTO.request.DelMethodDisableCommand;
import top.uhyils.usher.pojo.DTO.request.MethodDisableQuery;
import top.uhyils.usher.pojo.cqe.DefaultCQE;
import top.uhyils.usher.pojo.cqe.command.base.AddCommand;
import top.uhyils.usher.pojo.entity.type.InterfaceName;
import top.uhyils.usher.pojo.entity.type.MethodName;
import top.uhyils.usher.protocol.rpc.ServiceControlProvider;
import top.uhyils.usher.rpc.annotation.RpcService;
import top.uhyils.usher.service.ServiceControlService;
import top.uhyils.usher.util.Asserts;

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
