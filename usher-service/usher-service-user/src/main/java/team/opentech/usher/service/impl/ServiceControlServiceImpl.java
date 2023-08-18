package team.opentech.usher.service.impl;


import team.opentech.usher.annotation.ReadWriteMark;
import team.opentech.usher.enums.ReadWriteTypeEnum;
import team.opentech.usher.pojo.DTO.MethodDisableDTO;
import team.opentech.usher.pojo.entity.MethodDisable;
import team.opentech.usher.pojo.entity.type.InterfaceName;
import team.opentech.usher.pojo.entity.type.MethodName;
import team.opentech.usher.repository.ServiceControlRepository;
import team.opentech.usher.service.ServiceControlService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author uhyils <247452312@qq.com>
 * @version 1.0
 * @date 文件创建日期 2021年08月27日 08时32分43秒
 */
@Service
@ReadWriteMark
public class ServiceControlServiceImpl implements ServiceControlService {

    @Autowired
    private ServiceControlRepository repository;

    @Override
    public Boolean getMethodDisable(InterfaceName interfaceName, MethodName methodName, ReadWriteTypeEnum methodType) {
        MethodDisable methodDisable = new MethodDisable(interfaceName.getInterfaceName(), methodName.getMethodName(), methodType);
        methodDisable.completionClassName();
        return methodDisable.checkInterfaceDisable(repository);
    }

    @Override
    public List<MethodDisableDTO> getAllMethodDisable() {
        return repository.findAll();
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public Boolean addOrEditMethodDisable(MethodDisableDTO dto) {
        MethodDisable methodDisable = new MethodDisable(dto);
        methodDisable.saveMethodDisable(repository);
        return true;
    }

    @Override
    @ReadWriteMark(type = ReadWriteTypeEnum.WRITE)
    public Boolean delMethodDisable(InterfaceName interfaceName, MethodName methodName) {
        MethodDisable methodDisable = new MethodDisable(interfaceName.getInterfaceName(), methodName.getMethodName());
        methodDisable.del(repository);
        return true;
    }
}
