package team.opentech.usher.facade.impl;

import team.opentech.usher.annotation.Facade;
import team.opentech.usher.context.UserInfoHelper;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.facade.ServiceControlFacade;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.DTO.RelegationDTO;
import team.opentech.usher.pojo.DTO.request.DelMethodDisableCommand;
import team.opentech.usher.pojo.cqe.command.base.AddCommand;
import team.opentech.usher.protocol.rpc.ServiceControlProvider;
import team.opentech.usher.rpc.annotation.RpcReference;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
