package top.uhyils.usher.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import top.uhyils.usher.pojo.cqe.ExecuteInstructionCommand;
import top.uhyils.usher.pojo.cqe.FindMsgCommand;
import top.uhyils.usher.pojo.entity.device.core.Device;
import top.uhyils.usher.repository.DeviceFactory;
import top.uhyils.usher.service.DeviceManageService;
import top.uhyils.usher.util.Asserts;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2024年12月02日 17时23分
 */
@Service
public class DeviceManageServiceImpl implements DeviceManageService {

    @Resource
    private DeviceFactory deviceFactory;

    @Override
    public Object executeInstruction(ExecuteInstructionCommand command) {
        Device device = deviceFactory.getDevice(command.getUniqueMark());
        Asserts.assertTrue(device != null, "设备未找到");
        return device.requestSync(command.getContext());
    }

    @Override
    public Object findMsg(FindMsgCommand command) {
        Device device = deviceFactory.getDevice(command.getUniqueMark());
        Asserts.assertTrue(device != null, "设备未找到");
        return device.requestSync(command.getRequest());
    }
}
