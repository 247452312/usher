package top.uhyils.usher.facade.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import top.uhyils.usher.annotation.Facade;
import top.uhyils.usher.context.UserInfoHelper;
import top.uhyils.usher.enums.ReadWriteTypeEnum;
import top.uhyils.usher.facade.ServiceControlFacade;
import top.uhyils.usher.pojo.DTO.MethodDisableDTO;
import top.uhyils.usher.pojo.DTO.RelegationDTO;
import top.uhyils.usher.pojo.DTO.request.DelMethodDisableCommand;
import top.uhyils.usher.pojo.cqe.command.base.AddCommand;
import top.uhyils.usher.protocol.rpc.ServiceControlProvider;
import top.uhyils.usher.rpc.annotation.RpcReference;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年09月28日 16时09分
 */
@Facade
public class ServiceControlFacadeImpl implements ServiceControlFacade {

    @RpcReference
    private ServiceControlProvider provider;

    @Override
    public boolean demotion(String serviceName, String methodName) {
        AddCommand<MethodDisableDTO> request = new AddCommand<>();
        request.setDto(MethodDisableDTO.build(serviceName, methodName, ReadWriteTypeEnum.READ.getCode()));
        return provider.addOrEditMethodDisable(request);
    }

    @Override
    public boolean recover(String serviceName, String methodName) {
        DelMethodDisableCommand request = new DelMethodDisableCommand();
        request.setClassName(serviceName);
        request.setMethodName(methodName);
        return provider.delMethodDisable(request);
    }

    @Override
    public void fillDisable(List<RelegationDTO> dtos) {
        List<MethodDisableDTO> result = provider.getAllMethodDisable(UserInfoHelper.makeCQE());
        for (RelegationDTO dto : dtos) {
            for (MethodDisableDTO disableDTO : result) {
                String className = disableDTO.getClassName();
                String methodName = disableDTO.getMethodName();
                if (Objects.equals(dto.getServiceName(), className) && Objects.equals(dto.getMethodName(), methodName)) {
                    Integer disableType = disableDTO.getDisableType();
                    dto.setDisable(disableType != 1);
                    break;
                }
            }
        }
    }

    @Override
    public void fillDisable(RelegationDTO dto) {
        fillDisable(Arrays.asList(dto));
    }
}
