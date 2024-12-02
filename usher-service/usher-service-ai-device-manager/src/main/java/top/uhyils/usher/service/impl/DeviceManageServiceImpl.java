package top.uhyils.usher.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.repository.DeviceFactoryImpl;
import top.uhyils.usher.service.DeviceManageService;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时23分
 */
@Service
public class DeviceManageServiceImpl implements DeviceManageService {

    @Resource
    private DeviceFactoryImpl deviceFactory;

    @Override
    public Boolean executeInstruction(ExecuteInstructionCommand command) {
        return null;
    }
}
